import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI{

    private JFrame mainWindow;//object
    private JPanel mainPanel;//object



    //private JPanel secPanel;


    //              |
    //Constructor   V
    public GUI() {

        mainWindow = new JFrame();//object

        mainPanel = new JPanel();//object


        mainPanel.setLayout(new GridLayout(2, 2));//Method
        //mainPanel.setBorder(new LineBorder(Color.red, 5));
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        //mainPanel.setSize(800,600);




        JButton buttonBTS = new JButton("Binary Tree Search");//Object
        buttonBTS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==buttonBTS){

                    BinarySearchTree bsTree = new BinarySearchTree();
                }






            }
        });

        JButton buttonAVL = new JButton("AVL Search");
        buttonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==buttonAVL){
                    AVLTree avlTree = new AVLTree();
                }
            }
        });


        GridBagConstraints ins = new GridBagConstraints();
        ins.insets = new Insets(10, 10, 10, 10);






        mainPanel.add(buttonBTS);//method
        mainPanel.add(buttonAVL);//method



        mainWindow.add(mainPanel);//Method
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Method
        mainWindow.setTitle("GUI");//Method
        mainWindow.setSize(800, 600);

        mainWindow.pack();//Method



        mainWindow.setVisible(true);//Method
        mainWindow.setLocationRelativeTo(null);

    }








































}
