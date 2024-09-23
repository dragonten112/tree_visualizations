

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {



    static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val){
            this.val = val;
            left= null;
            right=null;

        }
    }

    static class BSTAlg {
         TreeNode root;

        public void insert(int val) {
            root = insertNr(root, val);

        }

        private TreeNode insertNr(TreeNode root, int val) {
            if (root == null) {
                root = new TreeNode(val);
                return root;
            }
            if (val < root.val) {
                root.left = insertNr(root.left, val);
            } else if (val > root.val) {
                root.right = insertNr(root.right, val);
            }
            return root;
        }

        public void delete(int val) {
            root = deleteNr(root, val);
        }

        private TreeNode deleteNr(TreeNode root, int val) {
            if (root == null) return null;
            if (val < root.val) {
                root.left = deleteNr(root.left, val);
            } else if (val > root.val) {
                root.right = deleteNr(root.right, val);
            } else {
                if (root.left == null) return root.right;
                else if (root.right == null) return root.left;
                root.val = minVal(root.right);
                root.right = deleteNr(root.right, root.val);

            }
            return root;
        }

        private int minVal(TreeNode root) {
            int minv = root.val;
            while (root.left != null) {
                minv = root.left.val;
                root = root.left;
            }
            return minv;
        }

        public List<TreeNode> findPath(int val) {
            List<TreeNode> path = new ArrayList<>();
            findPathRec(root, val, path);
            return path;
        }

        private boolean findPathRec(TreeNode root, int val, List<TreeNode> path) {
            if (root == null) return false;
            path.add(root);
            if (root.val == val) return true;
            if (val < root.val) {
                if (findPathRec(root.left, val, path)) return true;
            } else {
                if (findPathRec(root.right, val, path)) return true;
            }
            path.removeLast();
            return false;
        }

        public int getHeight() {
            return getHeight(root);
        }

        private int getHeight(TreeNode node) {
            if (node == null) {
                return 0;
            }
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }

        public boolean contains(int val) {
            return containsRec(root, val);
        }

        private boolean containsRec(TreeNode node, int val) {
            if (node == null) {
                return false;
            }
            if (val == node.val) {
                return true;
            } else if (val < node.val) {
                return containsRec(node.left, val);
            } else {
                return containsRec(node.right, val);
            }
        }

    }



    public BinarySearchTree() {

        //Creare Frame
        JFrame btsFrame = new JFrame("Binary Tree Search");
        btsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btsFrame.setSize(800, 600);

        btsFrame.setLocationRelativeTo(null);
        btsFrame.setVisible(true);

        //Creare Panel-uri
        JPanel btsPanel = new JPanel();
        btsPanel.setLayout(new BorderLayout());
        //btsPanel.setBorder(new LineBorder(Color.red, 5));


        VisualizationPanel canvas = new VisualizationPanel();
        canvas.setLayout(new BorderLayout());

        //canvas.setBorder(new LineBorder(Color.blue,5));

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1,0));
        //controlPanel.setBorder(new LineBorder(Color.green,5));





        //Creare butoane
        JButton btsInsert = new JButton("Insert");
        JTextField txtInsert = new JTextField(4);
        btsInsert.addActionListener(e -> {
            try {
                int val = Integer.parseInt(txtInsert.getText());
                canvas.insertVal(val);
                txtInsert.setText(null);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(btsFrame,"Introduceti un numar valid");
            }
                }
                );


        JButton btsDelete = new JButton("Delete");
        JTextField txtDelete = new JTextField(4);
        btsDelete.addActionListener(e -> {
            try{
                int val = Integer.parseInt(txtDelete.getText());
                canvas.deleteVal(val);
                txtDelete.setText(null);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(btsFrame,"Introduceti un numar valid");
            }
        });

        JButton btsFind = new JButton("Find");
        JTextField txtFind = new JTextField(4);
        btsFind.addActionListener(e -> {
                    try {
                        int val = Integer.parseInt(txtFind.getText());
                        canvas.findVal(val);
                        txtFind.setText(null);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(btsFrame, "Please enter a valid integer");
                    }
                }
                );

        //Adaugare butoane in panel

        btsPanel.add(controlPanel,BorderLayout.NORTH); //Adaugare Panel cu butoane(controlPanel) in Panelul Principal(btsPanel)



        controlPanel.add(btsInsert);

        controlPanel.add(btsDelete);

        controlPanel.add(btsFind);

        controlPanel.add(txtInsert);

        controlPanel.add(txtDelete);

        controlPanel.add(txtFind);

        btsPanel.add(canvas);  // Adaugare Canvas(canvas) in Panel-ul Principal(btsPanel)



        //Adaugare panel-uri in frame
        btsFrame.add(btsPanel);

    }



    static class VisualizationPanel extends JPanel {
        private final BSTAlg bst;
        private List<TreeNode> path;
        private TreeNode targetNode;  // Track the target node

        public VisualizationPanel() {
            bst = new BSTAlg();
        }

        public void insertVal(int val) {
            if (bst.getHeight() >= 6){
                JOptionPane.showMessageDialog(this,"Nu se mai poate adauga acest numar. Limita inaltimii arborelui a fost atinsa.");
                return;
            }
            if (bst.contains(val)) {
                JOptionPane.showMessageDialog(this, "Nu se poate insera. Valoarea exista in arbore.");
                return;
            }
            bst.insert(val);
            path = null; // Clear the path to ensure it is recalculated correctly
            targetNode = null; // Clear the target node
            repaint();
        }

        public void deleteVal(int val) {
            bst.delete(val);
            path = null; // Clear the path to ensure it is recalculated correctly
            targetNode = null; // Clear the target node
            repaint();
        }

        public void findVal(int val) {
            path = bst.findPath(val);
            if (path != null && !path.isEmpty()) {
                targetNode = path.getLast(); // Set the target node
            } else {
                targetNode = null;
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, bst.root, getWidth() / 2, 30, getWidth() / 4);
        }

        private void drawTree(Graphics g, TreeNode node, int x, int y, int xOffset) {
            if (node == null) return;

            boolean highlight = path != null && path.contains(node);

            if (highlight) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }

            g.drawOval(x - 15, y - 15, 30, 30);
            g.drawString(Integer.toString(node.val), x - 7, y + 5);

            if (node.left != null) {
                if (highlight && path.contains(node.left) && node != targetNode) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.drawLine(x - 5, y + 5, x - xOffset + 5, y + 50 - 5);
                drawTree(g, node.left, x - xOffset, y + 50, xOffset / 2);
            }

            if (node.right != null) {
                if (highlight && path.contains(node.right) && node != targetNode) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.drawLine(x + 5, y + 5, x + xOffset - 5, y + 50 - 5);
                drawTree(g, node.right, x + xOffset, y + 50, xOffset / 2);
            }
        }
    }






}


//Panel p = new Panel();
//     p.setLayout(new BorderLayout());
//     p.add(new Button("Okay"), BorderLayout.SOUTH);