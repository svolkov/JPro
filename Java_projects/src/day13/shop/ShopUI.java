package day13.shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ShopUI {

	private Stock stock;
    private JFrame f,fCart;
    private JTextField tf;
    private JTextField tf1;
    private List <JRadioButton> arrayRButton;
	private List <Goods> goodsList;

    private JPanel mainPanel;
    private ButtonGroup group;
    Customer customer;
	ShoppingCart shopCart;
    private JPanel radioPanel ;
    private JPanel attributePanel;
	private JPanel pricePanel;
    private JPanel quantPanel ;
    private JPanel basePanel;
    private JLabel labelColumn1 ;
    private JLabel labelColumn2 ;
    private JLabel labelColumn3 ;
	private JLabel labelColumn4;
    private JPanel panel;
    private JPanel panelTable;
	private JPanel panelCart;
	private JFrame frameChoise;
	private JFrame frameJTabel;
	private List <Transaction> register;
	private Date dateRef;


	public ShopUI(Date dateRef, Stock stock){
		register = new ArrayList<>();
		this.stock = stock;
		this.dateRef = dateRef;

        runShop();
//		f = new JFrame("Bookshop");
//        JMenuBar menuBar = new JMenuBar();
//        JMenu menu = new JMenu("Actions");
//        JMenuItem menuItem = new JMenuItem("Bay");
//        menu.add(menuItem);
//        menuBar.add(menu);
//        menuItem.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				TODO Auto-generated method stub
//
//				f.remove(panelTable);
//				mainPanel = createPanel();
//				f.add(mainPanel);
//				f.pack();
//				f.repaint();
//			}
//        });
//
//		panelTable = createTable();
//
//		f.setJMenuBar(menuBar);
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setMinimumSize(new Dimension(800, 600));
//      	f.add(panelTable);
//
//		f.pack();
//		f.setVisible(true);
	}

    private void runShop(){
        f = new JFrame("Bookshop");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Actions");
        JMenuItem menuItem = new JMenuItem("Bay");
        menu.add(menuItem);
        JMenuItem menuItem1 = new JMenuItem("Show transaction");
        menu.add(menuItem1);
        menuBar.add(menu);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // start new thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        createBayFrame();
                    }
                }).start();
//                                           f.remove(panelTable);
//                                           mainPanel = createPanel();
//                                           f.add(mainPanel);
//                                           f.pack();
//                                           f.repaint();
//           }
//        }
            }
        });
        menuItem1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
            //start new thread
             //   panelTable = createTable();
            }
        });


        f.setJMenuBar(menuBar);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(800, 600));
      //  f.add(panelTable);

        f.pack();
        f.setVisible(true);
    }



	private void createBayFrame(){
		JFrame  jFrame = new JFrame("Choose Your Book!");

        JPanel jPanel = new JPanel();

		if(stock.isEmpty()){
			JLabel labelEmpty = new JLabel("Shop is empty!");
            jPanel.add(labelEmpty);
			//return panel;
		}

        jPanel.setLayout(new GridBagLayout());

		if (customer==null) {
			JLabel label = new JLabel("Input Your name:");
            jPanel.add(label, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START,
					GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
			tf = new JTextField();
			tf.setColumns(10);
            jPanel.add(tf,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL,new Insets(0,0,10,0),0,0));
	    }else{
			JLabel l1 = new JLabel("Customer:");
            jPanel.add(l1, new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.LINE_START,
					GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));

			JLabel l2 = new JLabel(customer.getLogin());
            jPanel.add(l2,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.LINE_START,
					GridBagConstraints.HORIZONTAL,new Insets(0,0,10,0),0,0));
		}

		group = new ButtonGroup();

		int  numberTypesOnStock = stock.getNumberTypesOnStock();
		radioPanel = new JPanel(new GridLayout(numberTypesOnStock+1,1,1,3));
		attributePanel = new JPanel(new GridLayout(numberTypesOnStock+1,1,1,9));
		pricePanel = new JPanel(new GridLayout(numberTypesOnStock+1,1,1,9));
		quantPanel = new JPanel(new GridLayout(numberTypesOnStock+1,1,1,9));
		labelColumn1 = new JLabel("Title");
		labelColumn1.setFont(new Font("Arial",Font.BOLD,14));
		radioPanel.add(labelColumn1);
		labelColumn2 = new JLabel("Author/Issue");
		labelColumn2.setFont(new Font("Arial",Font.BOLD,14));
		attributePanel.add(labelColumn2);
		labelColumn3 = new JLabel("Price (USD):");
		labelColumn3.setFont(new Font("Arial",Font.BOLD,14));
		pricePanel.add(labelColumn3);
		labelColumn4 = new JLabel("Shop has:");
		labelColumn4.setFont(new Font("Arial",Font.BOLD,14));
		quantPanel.add(labelColumn4);

		arrayRButton = new ArrayList<>();

        goodsList = stock.getListOfGoods();
		for(int i = 0; i<goodsList.size();i++){
			Goods g = goodsList.get(i);
			arrayRButton.add(i, new JRadioButton(g.getName(), false));
			group.add(arrayRButton.get(i));
			radioPanel.add(arrayRButton.get(i));
			attributePanel.add(new JLabel(stock.getAttribute(g.getClass(),g.getName())));
			pricePanel.add(new JLabel(stock.getStringPrice(g.getClass(),g.getName())));
			quantPanel.add(new JLabel(stock.getStringNumberOfGoods(g.getClass(),g.getName())));
		}

		basePanel = new JPanel(new GridBagLayout());
		basePanel.add(radioPanel,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,0,0),25,0));
		basePanel.add(attributePanel,new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.LINE_START,
								0,new Insets(0,0,0,0),25,0));
		basePanel.add(pricePanel,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,0,0),25,0));
		basePanel.add(quantPanel,new GridBagConstraints(3,0,1,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,0,0),25,0));
		panel.add(basePanel,new GridBagConstraints(0,1,3,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,0,0),25,0));
		basePanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));


		JLabel label1 = new JLabel("Please, choose one of the books and it's required quantity");
		label1.setFont(new Font("Arial",Font.PLAIN,11));

        jPanel.add(label1,new GridBagConstraints(0,2,3,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,10,0),0,0));

		JLabel label2 = new JLabel("Input quantity:");
        jPanel.add(label2,new GridBagConstraints(0,3,1,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,10,0),0,0));

		tf1 = new JTextField(3);
        jPanel.add(tf1,new GridBagConstraints(1,3,1,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,10,0),0,0));

		JButton button1 = new JButton("Place in the Shopping Cart");//
        jPanel.add(button1,new GridBagConstraints(1,4,1,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,0,0),25,0));

		button1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(customer == null) {
					if (!tf.getText().isEmpty()) {
						customer = new Customer();
						shopCart = new ShoppingCart();
					} else {
						System.out.println("The name is empty. Input, please!");
						return;
					}
				}
				if(tf1.getText().isEmpty()){
					System.out.println("The quantity is empty. Input, please!");
					return;
				}
				//check radioButtons
				int indexOfRButton=0;
				while(indexOfRButton < arrayRButton.size()){
				  if(arrayRButton.get(indexOfRButton).isSelected()){
					  break;
				  }else{
					  indexOfRButton++;
				  }
				}
				if(indexOfRButton >= arrayRButton.size()){
					System.out.println("Please, choose the product!");
					return;
				}

				Goods g = goodsList.get(indexOfRButton);
				int numberGoodsToDelete = Integer.parseInt(tf1.getText());
				if(stock.getNumberOfGoods(g.getClass(),g.getName())>=numberGoodsToDelete){
//					shopCart.add(new Transaction("01.01.01",customer.getLogin(),g.getName(),
//							g.getPrice(),numberGoodsToDelete));
//					stock.remove(g.getClass(),g.getName(),numberGoodsToDelete);
				}else{
					System.out.println("Not enough goods. Try less.");
				}

                createCartFrame();

			//	f.remove(jPanel);
				panelTable = createTable();
				f.add(panelTable);
				f.pack();
				f.repaint();
			}
		});

		JButton button2 = new JButton("Exit");
        jPanel.add(button2,new GridBagConstraints(2,4,1,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,0,0),25,0));
		
		button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					System.exit(0);
			}
		});
		//return panel;
	}

	private void createCartFrame(){

		fCart = new JFrame("Shopping Cart");
				fCart.setMinimumSize(new Dimension(500, 200));

		panelCart = new JPanel();
		panelCart.setLayout(new GridBagLayout());

		JLabel l1 = new JLabel("Customer:");
		panelCart.add(l1, new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.LINE_START,
				GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));

		JLabel l2 = new JLabel(customer.getLogin());
		panelCart.add(l2,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL,new Insets(0,0,10,0),0,0));

		JLabel l3 = new JLabel("Purchase price:");
		panelCart.add(l3, new GridBagConstraints(1,1,1,1,0,0,GridBagConstraints.LINE_START,
				GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));
		JLabel l4 = new JLabel(Double.toString(shopCart.getSum())+"USD");
		panelCart.add(l4, new GridBagConstraints(2,1,1,1,0,0,GridBagConstraints.LINE_START,
				GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));

		JLabel l5 = new JLabel("Discount:");
		panelCart.add(l5, new GridBagConstraints(1,2,1,1,0,0,GridBagConstraints.LINE_START,
				GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));
		JLabel l6 = new JLabel(Integer.toString(shopCart.getDiscount())+"%");
		panelCart.add(l6, new GridBagConstraints(2,2,1,1,0,0,GridBagConstraints.LINE_START,
				GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));
		JLabel l7 = new JLabel("Final price:");
		panelCart.add(l7, new GridBagConstraints(1,3,1,1,0,0,GridBagConstraints.LINE_START,
				GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));
		JLabel l8 = new JLabel(Double.toString(shopCart.getEndSum())+"USD");
		panelCart.add(l8, new GridBagConstraints(2,3,1,1,0,0,GridBagConstraints.LINE_START,
				GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));

     // DECLAINE selection button must be here

		JButton b1 = new JButton("Add more goods");
		panelCart.add(b1,new GridBagConstraints(1,4,1,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,0,0),25,0));

		b1.addActionListener(new ActionListener(){
           JPanel mPanel;

			@Override
			public void actionPerformed(ActionEvent e) {

				fCart.dispose();
				frameChoise = new JFrame("Choose our products");
				frameChoise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frameChoise.setMinimumSize(new Dimension(800, 600));
	//			mPanel = createPanel();
				frameChoise.add(mPanel);
				frameChoise.pack();
				frameChoise.setVisible(true);
			}
		});
		JButton b2 = new JButton("Buy");
		panelCart.add(b2,new GridBagConstraints(2,4,1,1,0,0,GridBagConstraints.LINE_START,
				0,new Insets(0,0,0,0),25,0));

		b2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				buy();
				frameJTabel = new JFrame("Shop Transactions");
				frameJTabel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frameJTabel.setMinimumSize(new Dimension(800, 600));
				frameJTabel.add(createTable());
				frameJTabel.pack();
				frameJTabel.setVisible(true);
			}
		});
		if(frameChoise!=null){
			frameChoise.dispose();
		}

		fCart.add(panelCart);
		fCart.pack();
		fCart.setVisible(true);
	}

	private JPanel createTable(){
		JPanel tablePanel = new JPanel();
		
		if(register.size() > 0){
		  f.remove(mainPanel);
		  String[] columnTitles = new String[]{"TID","Date","Client","Goods","Price","Quantity"};
		  Object[][] data = new Object[register.size()][];

		  for(int i=0; i<register.size(); i++){
			Transaction t = register.get(i);
			data[i] = new Object[]{new Integer(i+1), register.get(i).getCurrentDate(),
					register.get(i).getClientLogin(), register.get(i).getElementName(),
					new Integer(register.get(i).getPrice()), new Integer(register.get(i).getQuantity())};
		  }
				
		  JTable table = new JTable(data,columnTitles);
		  table.getColumnModel().getColumn(1).setPreferredWidth(120);
		
		  JScrollPane scrollPane = new JScrollPane(table);
		  tablePanel.add(scrollPane);
		}else{
			JLabel label = new JLabel("No transactions.");
			tablePanel.add(label);
		}
		return tablePanel;
	}

	private void buy(){
// The process of the removing the goods from the store must be here
// The process of the transaction registering must be here
		for(int i=0;i<shopCart.getSize();i++){
			shopCart.getItem(i).setCurrentDate(dateRef.currentData);
            register.add(shopCart.getItem(i));
		}
	}


}
