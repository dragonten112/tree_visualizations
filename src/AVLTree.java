import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class AVLTree {


    public AVLTree() {
        JFrame avlFrame = new JFrame("AVL Tree Search");
        avlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        avlFrame.setSize(800, 600);

        avlFrame.setLocationRelativeTo(null);
        avlFrame.setVisible(true);

        //Creare Panel-uri
        JPanel avlPanel = new JPanel();
        avlPanel.setLayout(new BorderLayout());
        //avlPanel.setBorder(new LineBorder(Color.red, 5));


        AVLTree.AVLVisualizationPanel avlcanvas = new AVLTree.AVLVisualizationPanel();
        avlcanvas.setLayout(new BorderLayout());

        //avlcanvas.setBorder(new LineBorder(Color.blue, 5));

        JPanel avlcontrolPanel = new JPanel();
        avlcontrolPanel.setLayout(new GridLayout(1, 0));
        //avlcontrolPanel.setBorder(new LineBorder(Color.green, 5));


        //Creare butoane
        JButton avlInsert = new JButton("Insert");
        JTextField txtavlInsert = new JTextField(4);



        JButton avlDelete = new JButton("Delete");
        JTextField txtavlDelete = new JTextField(4);


        JButton avlFind = new JButton("Find");
        JTextField txtavlFind = new JTextField(4);


        //Adaugare butoane in panel

        avlPanel.add(avlcontrolPanel, BorderLayout.NORTH); //Adaugare Panel cu butoane(controlPanel) in Panelul Principal(btsPanel)


        avlcontrolPanel.add(avlInsert);

        avlcontrolPanel.add(avlDelete);

        avlcontrolPanel.add(avlFind);

        avlcontrolPanel.add(txtavlInsert);

        avlcontrolPanel.add(txtavlDelete);

        avlcontrolPanel.add(txtavlFind);

        avlPanel.add(avlcanvas);  // Adaugare Canvas(canvas) in Panel-ul Principal(btsPanel)


        //Adaugare panel-uri in frame
        avlFrame.add(avlPanel);



        //Adaugare Action Listeners
        avlInsert.addActionListener(e -> {
            try {
                int val = Integer.parseInt(txtavlInsert.getText());
                avlcanvas.insertVal(val);
                txtavlInsert.setText(null);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(avlFrame, "Introdu un un numar valid.");
            }
        });

        avlDelete.addActionListener(e -> {
            try {
                int val = Integer.parseInt(txtavlDelete.getText());
                avlcanvas.deleteVal(val);
                txtavlDelete.setText(null);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(avlFrame, "Introdu un numar valid.");
            }
        });

        avlFind.addActionListener(e -> {
            try {
                int val = Integer.parseInt(txtavlFind.getText());
                avlcanvas.findVal(val);
                txtavlFind.setText(null);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(avlFrame, "Introdu un numar valid.");
            }
        });






    }




    static class TreeNode {
        int val;
        int height;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
            height = 1;
        }
    }

    static class AVLAlg {
        TreeNode root;

        private int height(TreeNode node) {
            return node == null ? 0 : node.height;
        }

        private int balanceFactor(TreeNode node) {
            return node == null ? 0 : height(node.left) - height(node.right);
        }

        private TreeNode rightRotate(TreeNode y) {
            TreeNode x = y.left;
            TreeNode T2 = x.right;

            x.right = y;
            y.left = T2;

            y.height = Math.max(height(y.left), height(y.right)) + 1;
            x.height = Math.max(height(x.left), height(x.right)) + 1;

            return x;
        }

        private TreeNode leftRotate(TreeNode x) {
            TreeNode y = x.right;
            TreeNode T2 = y.left;

            y.left = x;
            x.right = T2;

            x.height = Math.max(height(x.left), height(x.right)) + 1;
            y.height = Math.max(height(y.left), height(y.right)) + 1;

            return y;
        }

        private TreeNode balance(TreeNode node) {
            int balance = balanceFactor(node);

            if (balance > 1) {
                if (balanceFactor(node.left) < 0) {
                    node.left = leftRotate(node.left);
                }
                return rightRotate(node);
            }

            if (balance < -1) {
                if (balanceFactor(node.right) > 0) {
                    node.right = rightRotate(node.right);
                }
                return leftRotate(node);
            }

            return node;
        }

        public void insert(int val) {
            root = insertRec(root, val);
        }

        private TreeNode insertRec(TreeNode node, int val) {
            if (node == null) {
                return new TreeNode(val);
            }

            if (val < node.val) {
                node.left = insertRec(node.left, val);
            } else if (val > node.val) {
                node.right = insertRec(node.right, val);
            } else {
                return node; // Duplicate values not allowed
            }

            node.height = 1 + Math.max(height(node.left), height(node.right));
            return balance(node);
        }

        public void delete(int val) {
            root = deleteRec(root, val);
        }

        private TreeNode deleteRec(TreeNode root, int val) {
            if (root == null) return root;

            if (val < root.val) {
                root.left = deleteRec(root.left, val);
            } else if (val > root.val) {
                root.right = deleteRec(root.right, val);
            } else {
                if ((root.left == null) || (root.right == null)) {
                    TreeNode temp = null;
                    if (temp == root.left) {
                        temp = root.right;
                    } else {
                        temp = root.left;
                    }

                    root = temp;
                } else {
                    TreeNode temp = minValueNode(root.right);
                    root.val = temp.val;
                    root.right = deleteRec(root.right, temp.val);
                }
            }

            if (root == null) return root;

            root.height = Math.max(height(root.left), height(root.right)) + 1;
            return balance(root);
        }

        private TreeNode minValueNode(TreeNode node) {
            TreeNode current = node;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

        public List<TreeNode> findPath(int val) {
            List<TreeNode> path = new ArrayList<>();
            findPathRec(root, val, path);
            return path;
        }

        private boolean findPathRec(TreeNode node, int val, List<TreeNode> path) {
            if (node == null) {
                return false;
            }

            path.add(node);

            if (node.val == val) {
                return true;
            }

            if (val < node.val) {
                if (findPathRec(node.left, val, path)) {
                    return true;
                }
            } else {
                if (findPathRec(node.right, val, path)) {
                    return true;
                }
            }

            path.remove(path.size() - 1);
            return false;
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

        public int getHeight() {
            return height(root);
        }
    }

    static class AVLVisualizationPanel extends JPanel {
        private final AVLAlg avl;
        private static List<TreeNode> path;
        private TreeNode targetNode;

        public AVLVisualizationPanel() {
            avl = new AVLAlg();
        }

        public void insertVal(int val) {
            if (avl.getHeight() >= 8) {
                JOptionPane.showMessageDialog(this, "Nu se poate insera. Limita a fost atinsa.");
                return;
            }
            if (avl.contains(val)) {
                JOptionPane.showMessageDialog(this, "Valoarea deja exista in arbore.");
                return;
            }
            avl.insert(val);
            path = null;
            targetNode = null;
            repaint();
        }

        public void deleteVal(int val) {
            avl.delete(val);
            path = null;
            targetNode = null;
            repaint();
        }

        public void findVal(int val) {
            path = avl.findPath(val);
            if (path != null && !path.isEmpty()) {
                targetNode = path.get(path.size() - 1);
            } else {
                targetNode = null;
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, avl.root, getWidth() / 2, 30, getWidth() / 4);
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
                if (highlight && path.contains(node.left) && node != targetNode){
                    g.setColor(Color.RED);
                }else {
                    g.setColor(Color.BLACK);
                }
                g.drawLine(x - 5, y + 5, x - xOffset + 5, y + 50 - 5);
                drawTree(g, node.left, x - xOffset, y + 50, xOffset / 2);
            }
            if (node.right != null) {
                if (highlight && path.contains(node.right) && node != targetNode){
                    g.setColor(Color.RED);
                }else {
                    g.setColor(Color.BLACK);
                }
                g.drawLine(x + 5, y + 5, x + xOffset - 5, y + 50 - 5);
                drawTree(g, node.right, x + xOffset, y + 50, xOffset / 2);
            }
        }
    }





}
