package serviceclasses;

import fieldobjects.*;
import tanks.AbstractTank;
import tanks.BT7;
import tanks.T34;
import tanks.Tiger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ActionField extends JPanel {

	final boolean COLORDED_MODE = false;

	private BattleField battleField;
    private T34 defender;
    private Bullet bullet;
    private AbstractTank aggressor;
    private Brick brick;
    private Water water;
    private Eagle eagle;
    private Rock rock;
    private JFrame frameBattle, frameUI;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    private JMenu subMenu;
    private JRadioButtonMenuItem rbMenuItem;
    private JRadioButton rButton1, rButton2;

    private ActionTextRecorder actionRecorder;
    private ActionTextPlayer actionPlayer;
    private JPanel startPanel;
    private ConcurrentLinkedQueue <Character> actionsQueue;
    private ConcurrentLinkedQueue <Character> networkActionsQueue;
    private Thread threadDefender, threadAggressor;
    private boolean threadAggressorActive, threadDefenderActive, threadTankActive;
    private int gameMode;
    private AtomicBoolean actionPerformed;


	public ActionField() throws Exception {

		battleField = new BattleField();
		brick = new Brick();
		water = new Water();
		eagle = new Eagle();
		rock = new Rock();
        actionsQueue = new ConcurrentLinkedQueue<>();
        actionPerformed = new AtomicBoolean();
        actionPerformed.set(true);
		frameUI = new JFrame("BATTLE FIELD, DAY 2");
		frameUI.setLocation(650, 50);
		frameUI.setMinimumSize(new Dimension(battleField.BF_WIDTH,
				battleField.BF_HEIGHT - 400));
		frameUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameUI.setVisible(true);
		welcomeGameUI();
	}

	public void runTheGame(boolean isReplay) throws Exception {
       // System.out.println("Game mode "+gameMode);
        actionRecorder = new ActionTextRecorder();

		battleField = new BattleField();
		defender = new T34(battleField, 128, 512, Direction.UP);

		if (rButton1.isSelected()) {
			aggressor = new Tiger(battleField, 128, 64, Direction.LEFT);
		} else {
			aggressor = new BT7(battleField, 512, 256, Direction.UP);
		}

        battleField.setAggressorX(aggressor.getX());
        battleField.setAggressorY(aggressor.getY());
        battleField.setDefenderX(defender.getX());
        battleField.setDefenderY(defender.getY());

		frameBattle = new JFrame("BATTLE FIELD, DAY 2");
		frameBattle.setLocation(750, 150);
		frameBattle.setMinimumSize(new Dimension(battleField.BF_WIDTH,
				battleField.BF_HEIGHT + 33));
		frameBattle.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frameBattle.getContentPane().add(this);
		frameBattle.pack();

      //  startKeyControl(frameBattle);
		frameBattle.setVisible(true);

        startDisplayRepainting();

        if(isReplay){
            startReplay();
            closeGame();
        }else {
            actionRecorder.init();
            switch (gameMode){
                case 0 :  startKeyControl(frameBattle);
                          startManualControlledGame(defender);
                          startAutomaticAggressor();
                          break;
                case 1 :  startNetworkGame(defender); // Telnet controlled
                          break;
                case 2 :  battleField.reloadFieldForCleaning();
                          defender.setX(0);
                          defender.setY(512);
                          aggressor = null;
                          startNetworkGame(defender);
                          break;
                case 3:   startKeyControl(frameBattle);
                          startMultiplayerNetworkMaster(aggressor);
                          break;
                case 4:   startKeyControl1(frameBattle);
                          startMultiplayerNetworkMaster(defender);
                          break;

            }

        }
	}

    private void startMultiplayerNetworkMaster(final AbstractTank tank){
        networkActionsQueue = new ConcurrentLinkedQueue<>();


        Thread threadTank = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;

                try {
                    socket = new Socket("localhost",8080);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final Socket finalSocket = socket;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Action tmpAction = new Action();
                        InputStream is;
                        char actionCode, greetingCode;

                        try {
                            is = finalSocket.getInputStream();
                            System.out.println("Client-receiver has established connection with GameServer");
                            do{
                                greetingCode = (char) is.read();
                            }
                            while(greetingCode!='y' && greetingCode!='n');

                            if(greetingCode == 'n'){
                                System.out.println("Requested role is busy!");
                                threadTankActive = false;
                            }else {
                                System.out.println("Requested role was committed");
                                startMultiplayerNetworkSlave(tank);
                            }

                            tmpAction.setPointer(tank);
                            while (threadTankActive) {
                                actionCode = (char) is.read();
                                System.out.println("Master Client received command: " + actionCode);
                                selectActionManualControl(tmpAction, actionCode);
                                actionPerformed.set(true);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream os;
                        char actionCode;

                        try {
                            os = finalSocket.getOutputStream();
                            if(tank == defender){
                                os.write('D');
                            }else{
                                os.write('A');
                            }

                            System.out.println("Master Client-sender has established connection with GameServer");
                            while (threadTankActive) {
                                if(!actionsQueue.isEmpty()){
                                    actionCode = actionsQueue.poll();
                                    if(actionPerformed.get()) {
                                        actionPerformed.set(false);
                                        os.write(actionCode);

                                    }
                                    System.out.println("Master Client-send: "+actionCode);
                                }
                                Thread.sleep(100);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        threadTankActive = true;
        threadTank.start();
    }

    private void startMultiplayerNetworkSlave(final AbstractTank tank){
        networkActionsQueue = new ConcurrentLinkedQueue<>();


        Thread threadTank = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;

                try {
                    socket = new Socket("localhost",8080);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final Socket finalSocket = socket;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Action tmpAction = new Action();
                        AbstractTank slaveTank = null;
                        InputStream is;
                        char actionCode, greetingCode;

                        try {
                            is = finalSocket.getInputStream();
                            System.out.println(" Slave Client-receiver has established connection with GameServer");
                            do{
                                greetingCode = (char) is.read();
                            }
                            while(greetingCode!='y' && greetingCode!='n');

                            if(greetingCode == 'n'){
                                System.out.println("Requested role is busy!");
                                threadTankActive = false;
                            }else {
                                if(tank == defender){
                                    slaveTank = aggressor;
                                }else{
                                    slaveTank = defender;
                                }
                                System.out.println("Requested role was committed");
                                tmpAction.setPointer(slaveTank);
                            }

                            while (threadTankActive) {
                                actionCode = (char) is.read();
                                System.out.println("Slave Client received command: " + actionCode);
                                selectActionManualControl(tmpAction, actionCode);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream os;
                        char actionCode;

                        try {
                            os = finalSocket.getOutputStream();
                            if(tank == defender){
                                os.write('a');
                            }else{
                                os.write('d');
                            }

                            System.out.println("Client-sender has established connection with GameServer");
                            while (threadTankActive) {
                                if(!actionsQueue.isEmpty()){
                                    actionCode = actionsQueue.poll();
                                    os.write(actionCode);
                                }
                                Thread.sleep(100);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        threadTankActive = true;
        threadTank.start();
    }
    private void startNetworkGame(final AbstractTank tank){
        networkActionsQueue = new ConcurrentLinkedQueue<>();

        Thread threadTank = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket;
                InputStream is = null;
                Action tmpAction = new Action();
                char actionCode;

                try {
                    socket = new Socket("localhost",8080);
                    is = socket.getInputStream();
                    } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Client-receiver has established connection with GameServer");
                tmpAction.setPointer(tank);

                while (threadTankActive) {
                        try {
                            actionCode = (char) is.read();
                            System.out.println("Client received command: "+actionCode);
                            selectActionManualControl(tmpAction, actionCode);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                 }
            }
        });
        threadTankActive = true;
        threadTank.start();
    }

    private void startAutomaticAggressor(){

       threadAggressor = new Thread(new Runnable() {
            @Override
            public void run() {
                Action tmpAction;

                while(threadAggressorActive){
                    tmpAction = aggressor.setup();
                    try {
                        actionRecorder.record(tmpAction);
                        selectAction(tmpAction);
                       } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadAggressorActive = true;
        threadAggressor.start();
    }

    private void startReplay() throws Exception {
        Action action;
        actionPlayer = new ActionTextPlayer();
        actionPlayer.init(defender,aggressor);
        while ((action = actionPlayer.read())!=null){
            selectAction(action);
        }
        actionPlayer.close();
    }

    private void selectAction(Action action) throws Exception {


        switch (action.getNextAct()) {
            case FIRE:
                processFire(action);
                synchronized (action.getPointer()){
                    action.getPointer().wait();
                }
                break;
            case MOVE:
                processMove(action);
                break;
            case TURN_RIGHT:
                turnRight(action.getPointer());
                break;
            case TURN_LEFT:
                turnLeft(action.getPointer());
                break;
        }
    }

    private void closeGame(){
//        if(actionRecorder.isActive()) {
//            actionRecorder.close();
//        }

        frameBattle.dispose();
    }

    private void welcomeGameUI() {
		if(startPanel!=null){
			frameUI.remove(startPanel);
		}
		startPanel = new JPanel();
		startPanel.setLayout(new GridBagLayout());

		JLabel label = new JLabel("Select the tank for Aggressor");
		startPanel.add(label, new GridBagConstraints(1, 0, 1, 1, 0, 0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(0, 0, 10, 0), 0, 0));

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(2, 1, 1, 1));
		ButtonGroup bGroup = new ButtonGroup();
		rButton1 = new JRadioButton("Tiger", true);
		bGroup.add(rButton1);
		radioPanel.add(rButton1);
		rButton2 = new JRadioButton("BT7", false);
		bGroup.add(rButton2);
		radioPanel.add(rButton2);
		radioPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		startPanel.add(radioPanel, new GridBagConstraints(1, 1, 1, 1, 0, 0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(0, 0, 10, 0), 0, 0));

        JLabel label1 = new JLabel("Select the game mode");
        startPanel.add(label1, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 0), 0, 0));

        String[] modes = {"Local","Network - telnet","Network - clean the field","Network Client Aggressor","Network Client Defender"};
        JComboBox<String> modesList = new JComboBox<>(modes);
        modesList.setSelectedIndex(0);
        gameMode = modesList.getSelectedIndex();
        modesList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox)e.getSource();
                gameMode = comboBox.getSelectedIndex();
              }
        });
        startPanel.add(modesList, new GridBagConstraints(1, 3, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 0), 0, 0));

		JButton button1 = new JButton("Start Game");
		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				new Thread() {
					public void run() {
						try {
							runTheGame(false);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}.start();
			}
		});
		startPanel.add(button1, new GridBagConstraints(1, 4, 1, 1, 0, 0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(0, 0, 10, 0), 0, 0));

		frameUI.add(startPanel);
		frameUI.pack();
		frameUI.repaint();
	}

	private void finishGameUI() {
        if(threadAggressor.isAlive()){
            //threadAggressor.interrupt();
            threadAggressorActive = false;
        }
        if(threadDefender.isAlive()){
           // threadDefender.interrupt();
            threadDefenderActive = false;
        }
        if(actionRecorder.isActive()) {
            actionRecorder.close();
        }
        frameUI.remove(startPanel);
		startPanel = new JPanel();
		startPanel.setLayout(new GridBagLayout());

		JLabel label = new JLabel("Game over!");
		startPanel.add(label, new GridBagConstraints(1, 0, 1, 1, 0, 0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(0, 0, 10, 0), 0, 0));
		JButton button2 = new JButton("Start New Game");
		button2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Thread() {
					public void run() {
						welcomeGameUI();
					}
				}.start();
			}
		});
		startPanel.add(button2, new GridBagConstraints(1, 2, 1, 1, 0, 0,
				GridBagConstraints.LINE_START, GridBagConstraints.NONE,
				new Insets(0, 0, 10, 0), 0, 0));
        JButton button3 = new JButton("Replay the Game");
        button3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                new Thread() {
                    public void run() {
                        try {
                            runTheGame(true);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        startPanel.add(button3, new GridBagConstraints(1, 4, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 0), 0, 0));
		frameUI.add(startPanel);
		frameUI.pack();
        frameUI.repaint();	
        closeGame();
        while (true);
	}

	private String getQuadrantXY(int v, int h) {
		return (v - 1) * 64 + "_" + (h - 1) * 64;
	}

	public String getQuadrant(int x, int y) {
		return Integer.toString(y / 64) + "_" + Integer.toString(x / 64);
	}

	public int getQuadrantV(int x) {
		return x / 64;
	}

	public int getQuadrantH(int y) {
		return y / 64;
	}

	private boolean processInterception(Bullet bullet) throws Exception {
		FieldObject tmpObject;

		int y = bullet.getY() / 64;
		int x = bullet.getX() / 64;
		System.out.print("Y:" + bullet.getY());
		System.out.println("X:" + bullet.getX());
		System.out.print("y:" + y);
		System.out.println("x:" + x);

		if ((y >= 0) && (y < 9) && (x >= 0) && (x < 9)) {
			tmpObject = battleField.getFieldObject(y, x);
			if (tmpObject instanceof Water || tmpObject instanceof Land) {
				return false;
			} else {
				if (tmpObject instanceof Rock) {
					bullet.destroy();
					return true;
				} else {
					if (tmpObject instanceof Eagle) {
						battleField.setFieldObject(y, x, new Land(x * 64,y * 64));
						System.out.println("Made empty");
						bullet.destroy();
						finishGameUI();
						return true;
					} else {
							if(tmpObject instanceof Brick) {
						       battleField.setFieldObject(y, x, new Land(x * 64,y * 64));
						       System.out.println("Made empty");
						       bullet.destroy();
						       return true;
						    }
					}
				}
			}
		}
		return false;
	}

	public void turnRight(AbstractTank tank) throws Exception {

		switch (tank.getDirection()) {
		case UP:
			tank.setDirection(Direction.RIGHT);
			break;
		case RIGHT:
			tank.setDirection(Direction.DOWN);
			break;
		case DOWN:
			tank.setDirection(Direction.LEFT);
			break;
		case LEFT:
			tank.setDirection(Direction.UP);
			break;
		}
		System.out.print("Direction" + tank.getDirection());

	}

	public void turnLeft(AbstractTank tank) throws Exception {

		switch (tank.getDirection()) {
		case UP:
			tank.setDirection(Direction.LEFT);
			break;
		case LEFT:
			tank.setDirection(Direction.DOWN);
			break;
		case DOWN:
			tank.setDirection(Direction.RIGHT);
			break;
		case RIGHT:
			tank.setDirection(Direction.UP);
			break;
		}
		System.out.print("Direction" + tank.getDirection());
		;
	}

	public void processMove(Action action) throws Exception {
		int step = 1;
        int stepX = 0;
        int stepY = 0;
		int covered = 0;


		AbstractTank tank = action.getPointer();
        Direction tankDirection = tank.getDirection();

        synchronized (this) {
            if ((tankDirection == Direction.UP && tank.getY() == 0)
                    || (tankDirection == Direction.DOWN && tank.getY() >= 512)
                    || (tankDirection == Direction.LEFT && tank.getX() == 0)
                    || (tankDirection == Direction.RIGHT && tank.getX() >= 512)
                    || battleField.getNextFieldObject(getQuadrantV(tank.getY()),
                    getQuadrantH(tank.getX()), tankDirection) instanceof Rock
                    || battleField.getNextFieldObject(getQuadrantV(tank.getY()),
                    getQuadrantH(tank.getX()), tankDirection) == null
                    || battleField.getNextFieldObject(getQuadrantV(tank.getY()),
                    getQuadrantH(tank.getX()), tankDirection) instanceof Eagle
                    || battleField.getNextFieldObject(getQuadrantV(tank.getY()),
                    getQuadrantH(tank.getX()), tankDirection) instanceof AbstractTank
//                    || ((tank instanceof T34) && (tankDirection == Direction.UP && tank.getY() <= 384))
                    || (tank instanceof T34)
                    && (battleField.getNextFieldObject(getQuadrantV(tank.getY()),
                    getQuadrantH(tank.getX()), tankDirection) instanceof Brick)) {
                System.out.println("[illegal move] direction: "
                        + tankDirection + " tankX: " + tank.getX()
                        + ", tankY: " + tank.getY());
                action.setActionResult(false);
                return;
            }
        }

		switch (tankDirection){
            case UP   : stepY = -step;
                        break;
            case DOWN : stepY = step;
                        break;
            case LEFT : stepX = -step;
                        break;
            case RIGHT : stepX = step;
                break;
        }

        boolean tankDefender = false;

        if(tank instanceof T34) {
            tankDefender = true;
        }

        while (covered < 64) {
            tank.updateX(stepX);
            tank.updateY(stepY);
            if(tankDefender) {
              battleField.setDefenderX(tank.getX());
              battleField.setDefenderY(tank.getY());
            }else {
              battleField.setAggressorX(tank.getX());
              battleField.setAggressorY(tank.getY());
            }
		    covered += step;
			Thread.sleep(tank.getMaxSpeed());
		}
       action.setActionResult(true);
	}

	public void processFire(Action action ) throws Exception {
        AbstractTank tank = action.getPointer();

        bullet = new Bullet(tank.getX()/* + 25*/, tank.getY()/* + 25*/,tank.getDirection(), action.getPointer());

        new Thread(new Runnable() {
            @Override
            public void run() {
                int covered = 0;
                int step = 1;

                while (bullet.getX() > 0 && bullet.getX() <= 576 && bullet.getY() > 0
                        && bullet.getY() <= 576) {
                    while (covered < 8/*64*/) {
                        if (bullet.getDirection() == Direction.UP) {
                            bullet.updateY(-step);
                            System.out.println("[move up] direction: "
                                    + bullet.getDirection() + " bulX: " + bullet.getX()
                                    + ", bulletY: " + bullet.getY());
                        } else if (bullet.getDirection() == Direction.DOWN) {
                            bullet.updateY(step);
                            System.out.println("[move down] direction: "
                                    + bullet.getDirection() + " bulX: " + bullet.getX()
                                    + ", bulletY: " + bullet.getY());
                        } else if (bullet.getDirection() == Direction.LEFT) {
                            bullet.updateX(-step);
                            System.out.println("[move left] direction: "
                                    + bullet.getDirection() + " bulX: " + bullet.getX()
                                    + ", bulletY: " + bullet.getY());
                        } else {
                            bullet.updateX(step);
                            System.out.println("[move right] direction: "
                                    + bullet.getDirection() + " bulX: " + bullet.getX()
                                    + ", bulletY: " + bullet.getY());
                        }
                        try {
                            if(isTargetHit(bullet)) {
                               bullet.destroy();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        covered += step;

                        try {
                            Thread.currentThread().sleep(bullet.getSpeed());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    covered = 0;
                    try {
                        if (processInterception(bullet)) {
                           Thread.currentThread().sleep(bullet.getSpeed());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                bullet.destroy();
            }
        }).start();

	}

	public void processDestroy(AbstractTank tank) throws Exception {

		if (tank instanceof Tiger) {
			if (((Tiger) tank).getArmor() > 0) {
				((Tiger) tank).setArmor(0);
				return;
			}
		}
		tank.setX(-100);
		tank.setY(-100);
		finishGameUI();
		}

    private void startDisplayRepainting() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    repaint();
                    try {
                        Thread.currentThread().sleep(1000 / 75);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private boolean isTargetHit(Bullet bullet) throws Exception {

        AbstractTank tank = bullet.getTank();
        boolean tankDamaged = false;
        boolean tankDefender = false;

        Direction bulletDirection = bullet.getDirection();
        int bulX = bullet.getX();
        int bulY = bullet.getY();
        int targetTankX;
        int targetTankY;

        if(tank instanceof T34) {
            targetTankX = battleField.getAggressorX();
            targetTankY = battleField.getAggressorY();
        }else{
            targetTankX = battleField.getDefenderX();
            targetTankY = battleField.getDefenderY();
            tankDefender = true;
        }

        if(bulletDirection == Direction.DOWN || bulletDirection == Direction.UP) {
            if ((targetTankX < bulX) && ((targetTankX + 64) > bulX)) {
                if ((targetTankY == bulY) || ((targetTankY + 64) == bulY)) {
                    tankDamaged = true;
                    if (tankDefender){
                        processDestroy(defender);
                    }else {
                        processDestroy(aggressor);
                    }
                }
            }
        }else{
            if ((targetTankY < bulY) && ((targetTankY + 64) > bulY)) {
                if ((targetTankX == bulX) || ((targetTankX + 64) == bulX)) {
                    tankDamaged = true;
                    if (tankDefender){
                        processDestroy(defender);
                    }else {
                        processDestroy(aggressor);
                    }
                }
            }
        }
      return tankDamaged;
    }

    private void startKeyControl(JFrame f){
      f.addKeyListener(new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {
              }
          @Override
          public void keyPressed(KeyEvent e) {
            if(actionsQueue.isEmpty()) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        System.out.println("Key Left");
                        actionsQueue.offer('L');
                        break;
                    case KeyEvent.VK_RIGHT:
                        System.out.println("Key Right");
                        actionsQueue.offer('R');
                        break;
                    case KeyEvent.VK_DOWN:
                        System.out.println("Key Down");
                        actionsQueue.offer('D');
                        break;
                    case KeyEvent.VK_UP:
                        System.out.println("Key Up");
                        actionsQueue.offer('U');
                        break;
                    case KeyEvent.VK_SPACE:
                        System.out.println("SPACE");
                        actionsQueue.offer('F');
                        break;
                }
            }
          }
          @Override
          public void keyReleased(KeyEvent e) {
          }
      });
    }

    private void startKeyControl1(JFrame f){
        f.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
              if(actionsQueue.isEmpty()) {
                  int keyCode = e.getKeyCode();
                  switch (keyCode) {
                      case KeyEvent.VK_NUMPAD4:
                          System.out.println("Num 4");
                          actionsQueue.offer('L');
                          break;
                      case KeyEvent.VK_NUMPAD6:
                          System.out.println("Num 6");
                          actionsQueue.offer('R');
                          break;
                      case KeyEvent.VK_NUMPAD2:
                          System.out.println("Num 2");
                          actionsQueue.offer('D');
                          break;
                      case KeyEvent.VK_NUMPAD8:
                          System.out.println("Num 8");
                          actionsQueue.offer('U');
                          break;
                      case KeyEvent.VK_NUMPAD5:
                          System.out.println("Num 5");
                          actionsQueue.offer('F');
                          break;
                  }
              }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void startManualControlledGame(final AbstractTank tank){

       threadDefender = new Thread(new Runnable() {
            @Override
            public void run() {
                Action tmpAction = new Action();
                char actionCode;

                tmpAction.setPointer(tank);

                while (threadTankActive) {
                  if(!actionsQueue.isEmpty()){
                    actionCode = actionsQueue.poll();
                      try {
                          selectActionManualControl(tmpAction, actionCode);
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }
                }
            }
        });
        threadTankActive = true;
          threadDefender.start();
    }

    private void selectActionManualControl(Action action, char code) throws Exception {

        switch (code) {
            case 'F':
                action.setNextAct(TankActions.FIRE);
                actionRecorder.record(action);
                processFire(action);
                synchronized (action.getPointer()){
                    action.getPointer().wait();
                }
                break;
            case 'U'|'D': action.setNextAct(TankActions.MOVE);
                actionRecorder.record(action);
                processMove(action);
                break;
            case 'R': action.setNextAct(TankActions.TURN_RIGHT);
                actionRecorder.record(action);
                turnRight(action.getPointer());
                break;
            case 'L':action.setNextAct(TankActions.TURN_LEFT);
                actionRecorder.record(action);
                turnLeft(action.getPointer());
                break;
        }

    }
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		FieldObject tmpObject;
		int i = 0;

		Color cc;
		for (int v = 0; v < 9; v++) {
			for (int h = 0; h < 9; h++) {
				if (COLORDED_MODE) {
					if (i % 2 == 0) {
						cc = new Color(252, 241, 177);
					} else {
						cc = new Color(233, 243, 255);
					}
				} else {
					cc = new Color(180, 180, 180);
				}
				i++;
				g.setColor(cc);
				g.clearRect(h * 64, v * 64, 64, 64);
			}
		}

		for (int v = 0; v < battleField.getDimentionY(); v++) {
			for (int h = 0; h < battleField.getDimentionX(); h++) {
				battleField.getFieldObject(v, h).draw(g);
			}
		}
		if (defender != null) {
			defender.draw(g);
		}
		if (aggressor != null) {
			aggressor.draw(g);
		}

		for (int v = 0; v < battleField.getDimentionY(); v++) {
			for (int h = 0; h < battleField.getDimentionX(); h++) {
				tmpObject = battleField.getFieldObject(v, h);
				if (tmpObject instanceof Water) {
					tmpObject.draw(g);
				}
			}
		}
		if(bullet != null) {
            bullet.draw(g);
        }
	}
}
