package day13.shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by syv on 23.04.15.
 */
public class ShopGUI {
    private List<Transaction> register;
    private Date dateRef;
    private Stock stock;
    private JFrame jFrame,fCart;
    private  JTable jTable;
    private JPanel panelBuy;
    private JPanel panelTransactions;
    private ShoppingCart shopCart;
    private LinkedList<Customer> customersList;
    private CustomerDB customerDB;



    public ShopGUI(Date dateRef, Stock stock, CustomerDB customerDB) {
        register = new ArrayList<>();
        this.stock = stock;
        this.dateRef = dateRef;
        this.customerDB = customerDB;
        customersList = new LinkedList<>();
        runShop();
    }

    private void runShop(){

        jFrame = new JFrame("Bookshop");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Actions");
        JMenuItem menuItem = new JMenuItem("Buy");
        menu.add(menuItem);
        JMenuItem menuItem1 = new JMenuItem("Show transactions");
        menu.add(menuItem1);
        menuBar.add(menu);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                startBuyProcess();
            }
        });
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                //   Display transaction
                displayTransactions();

            }
        });


        jFrame.setJMenuBar(menuBar);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setMinimumSize(new Dimension(800, 600));

        jFrame.pack();
        jFrame.setVisible(true);
    }

    private void startBuyProcess(){
       if(panelTransactions != null){
           jFrame.remove(panelTransactions);
       }

       panelBuy = new JPanel(new GridBagLayout());

       if(stock.isEmpty()){
            JLabel labelEmpty = new JLabel("Shop is empty!");
            panelBuy.add(labelEmpty);
       }else {
          final List <Goods> goodsList = stock.getListOfGoods();
          jTable = createGoodsTable(goodsList);
          JScrollPane jScrollPane = new JScrollPane(jTable);
          panelBuy.add(jScrollPane, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START,
                  GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
           JButton button1 = new JButton("View Shopping Cart");//
           panelBuy.add(button1, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START,
                   0, new Insets(0, 0, 0, 0), 25, 0));

           button1.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                  createShoppingCartFrame(goodsList);
               }
           });
           JButton button2 = new JButton("Exit");
           panelBuy.add(button2, new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START,
                   0, new Insets(0, 0, 0, 0), 25, 0));

           button2.addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent e) {
                   System.exit(0);
               }
           });
       }
        jFrame.add(panelBuy);
        jFrame.pack();
        jFrame.repaint();
        return;
    }

    private JTable createGoodsTable(List <Goods> listOfGoods){
        String[] columnNames = {"Title", "Author/Issue", "Price, usd", "N in the store", "N to buy"};
        Object[][] tableData = new Object[listOfGoods.size()][];
        Goods g;

        for(int i = 0; i < listOfGoods.size(); i++){
            tableData[i] = new Object[5];
            g = listOfGoods.get(i);
            tableData[i][0] = g.getName();
            if (g instanceof Book){
                tableData[i][1] = ((Book)g).getAuthor();
            }else {
                tableData[i][1] = ((Magazine)g).getIssueNumber();
            }
            tableData[i][2] = new Integer(g.getPrice());
            tableData[i][3] = new Integer(stock.getNumberOfGoods(g.getClass(), g.getName()));
            tableData[i][4] = "0";
        }
        return new JTable(tableData,columnNames);
    }

    private void createShoppingCartFrame(final List <Goods> listOfGoods){

        int numbersToBay;
        Goods g;
        shopCart = new ShoppingCart();

        for (int i = 0; i < jTable.getRowCount(); i++){
          numbersToBay = Integer.parseInt((String)jTable.getValueAt(i, 4));
          if(numbersToBay!=0){
              g = listOfGoods.get(i);
              shopCart.add(new Transaction(i,"01.01.01","customer",g.getName(),
                      g.getPrice(),numbersToBay));
          }
        }
        fCart = new JFrame("Shopping Cart");
        fCart.setMinimumSize(new Dimension(500, 300));
        fCart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel panelCart = new JPanel();
        panelCart.setLayout(new GridBagLayout());

        JLabel l1 = new JLabel("You have made selection. Thanks for Your choice!");
        l1.setFont(new Font("Arial",Font.BOLD,14));
        panelCart.add(l1, new GridBagConstraints(1,0,2,1,0,0,GridBagConstraints.LINE_START,
                GridBagConstraints.NONE,new Insets(0,0,10,0),0,0));

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

        JLabel label = new JLabel("Input Your name:");
        panelCart.add(label, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.LINE_START,
                GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
        final JTextField tf = new JTextField();
        tf.setColumns(10);
        panelCart.add(tf,new GridBagConstraints(2,4,1,1,0,0,GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,new Insets(0,0,10,0),0,0));

        JLabel label1 = new JLabel("Input Your e-mail:");
        panelCart.add(label1, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.LINE_START,
                GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
        final JTextField tf1 = new JTextField();
        tf1.setColumns(15);
        panelCart.add(tf1,new GridBagConstraints(2,5,1,1,0,0,GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,new Insets(0,0,10,0),0,0));

        JButton button1 = new JButton("Buy");//
        panelCart.add(button1,new GridBagConstraints(1,6,1,1,0,0,GridBagConstraints.LINE_START,
                0,new Insets(0,0,0,0),0,0));



        button1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

 //              Add new customer
                    if (!tf.getText().isEmpty() && !tf1.getText().isEmpty()) {
                        Customer customer = new Customer();
                        customer.setLogin(tf.getText());
                        customer.setEmail(tf1.getText());
                        customerDB.add(customer);
                    } else {
                        System.out.println("The name or e-mail is empty. Input, please!");
                        return;
                    }

                    Transaction transaction;
                    for(int i = 0; i < shopCart.getSize();i++){
                        //            Register new transaction
                        transaction = shopCart.getItem(i);
                        transaction.setClientLogin(tf.getText());
                        transaction.setCurrentDate(dateRef.currentData);
                        register.add(transaction);
                        //            Remove bought goods from the stock
                        stock.remove(listOfGoods.get(transaction.getIndexInGoodsList()).getClass(),transaction.getElementName(),
                                transaction.getQuantity());
                    }


                // Clean JFrame or panelBuy
                shopCart = null;
                jFrame.remove(panelBuy);
                jFrame.repaint();
                fCart.dispose();
              }


        });

        JButton button2 = new JButton("Close");
        panelCart.add(button2, new GridBagConstraints(2, 6, 1, 1, 0, 0, GridBagConstraints.LINE_START,
                0, new Insets(0, 0, 0, 0), 0, 0));

        button2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                shopCart = null;

                fCart.dispose();
            }
        });

        fCart.add(panelCart);
        fCart.pack();
        fCart.setVisible(true);
    }

    private void displayTransactions(){
        panelTransactions = new JPanel(new GridBagLayout());

        if(register.isEmpty()){
            JLabel labelEmpty = new JLabel("No transactions");
            panelTransactions.add(labelEmpty);
        }else {

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
            panelTransactions.add(scrollPane);
        }
        jFrame.add(panelTransactions);
        jFrame.pack();
        jFrame.repaint();
        return;
    }
}

