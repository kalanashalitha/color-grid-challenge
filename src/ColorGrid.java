import java.util.*;
import java.util.stream.Collectors;

public class ColorGrid {

    static Random random = new Random();
    Node[][] colorGridArr;
    String[] colors = {"r", "g", "b"};

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter row count");
        int rowCount = Integer.parseInt(myObj.nextLine());  // Read user input
        System.out.println("Enter column count");
        int columnCount = Integer.parseInt(myObj.nextLine());  // Read user input
        ColorGrid colorGrid = new ColorGrid();
        colorGrid.createColorGrid(rowCount, columnCount);
        colorGrid.search();
    }

    void createColorGrid(int rows, int cols) {
        colorGridArr = new Node[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                colorGridArr[r][c] = new Node(c, r, getRandom(colors));
            }
        }
        // print the grid on the console
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(colorGridArr[r][c].getColorCode() + " ");
            }
            System.out.println();
        }
    }

    void search() {
        HashMap<String, Integer> colorChunkNumberMap = new HashMap<>();
        for (String color : colors) {
            colorChunkNumberMap.put(color, 0);
        }
        for (int r = 0; r < colorGridArr.length; r++) {
            for (int c = 0; c < colorGridArr[0].length; c++) {
                Node currentNode = colorGridArr[r][c];
                ArrayList<Node> surroundingNodes = new ArrayList<>();
                if (0 <= c - 1) {
                    surroundingNodes.add(colorGridArr[r][c - 1]);
                }
                if (colorGridArr[0].length > c + 1) {
                    surroundingNodes.add(colorGridArr[r][c + 1]);
                }
                if (0 <= r - 1) {
                    surroundingNodes.add(colorGridArr[r - 1][c]);
                }
                if (colorGridArr.length > r + 1) {
                    surroundingNodes.add(colorGridArr[r + 1][c]);
                }
                List<Node> colorMatched = surroundingNodes
                        .stream()
                        .filter(node -> node.getColorCode().equals(currentNode.getColorCode()))
                        .collect(Collectors.toList());

                colorMatched.stream().filter(node -> 0 != node.getChunkNumber()).findFirst().ifPresent(node -> {
                    colorMatched.forEach(node1 -> node1.setChunkNumber(node.getChunkNumber()));
                });

                List<Node> colorMatchedWithChunkNo = surroundingNodes
                        .stream()
                        .filter(node -> node.getColorCode().equals(currentNode.getColorCode()) && 0 != node.getChunkNumber())
                        .collect(Collectors.toList());

                if (colorMatchedWithChunkNo.isEmpty()) {
                    colorMatchedWithChunkNo = surroundingNodes
                            .stream()
                            .filter(node -> node.getColorCode().equals(currentNode.getColorCode()))
                            .collect(Collectors.toList());
                }
                colorMatchedWithChunkNo.stream().findFirst().ifPresent(node -> {
                    if (0 != node.getChunkNumber()) {
                        currentNode.setChunkNumber(node.getChunkNumber());
                    } else {
                        if (0 == colorChunkNumberMap.get(node.getColorCode())) {
                            colorChunkNumberMap.put(currentNode.getColorCode(), 1);
                            currentNode.setChunkNumber(1);
                        } else {
                            colorChunkNumberMap.put(currentNode.getColorCode(), colorChunkNumberMap.get(currentNode.getColorCode()) + 1);
                            currentNode.setChunkNumber(colorChunkNumberMap.get(currentNode.getColorCode()));
                        }
                    }
                });
                if (colorMatched.isEmpty()) {
                    colorChunkNumberMap.put(currentNode.getColorCode(), colorChunkNumberMap.get(currentNode.getColorCode()) + 1);
                    currentNode.setChunkNumber(colorChunkNumberMap.get(currentNode.getColorCode()));
                }
            }
        }
        System.out.println("");
        System.out.println("Divided to chunks :");
        System.out.println("");
        for (int r = 0; r < colorGridArr.length; r++) {
            for (int c = 0; c < colorGridArr[0].length; c++) {
                System.out.print(colorGridArr[r][c].getColorCode() + colorGridArr[r][c].getChunkNumber() + " ");
            }
            System.out.println();
        }
        HashMap<String, ArrayList<Node>> colorChunkMap = new HashMap<>();
        for (int r = 0; r < colorGridArr.length; r++) {
            for (int c = 0; c < colorGridArr[0].length; c++) {
                String chunk = colorGridArr[r][c].getColorCode() + colorGridArr[r][c].getChunkNumber();
                ArrayList<Node> nodes = colorChunkMap.get(chunk);
                if (null == nodes) {
                    ArrayList ar = new ArrayList<>();
                    ar.add(colorGridArr[r][c]);
                    colorChunkMap.put(chunk, ar);
                } else {
                    nodes.add(colorGridArr[r][c]);
                    colorChunkMap.put(chunk, nodes);
                }
            }
        }
        System.out.println();
        String largestChunkName = "";
        int largestSize = 0;
        for (Map.Entry<String, ArrayList<Node>> map : colorChunkMap.entrySet()) {
            if ("".equals(largestChunkName)) largestChunkName = map.getKey();
            if (0 == largestSize) {
                largestSize = map.getValue().size();
            } else if (largestSize < map.getValue().size()) {
                largestChunkName = map.getKey();
                largestSize = map.getValue().size();
            }
        }
        System.out.println("the largest connecting block of\n" +
                "nodes with the same color is " + largestChunkName + ".");
        System.out.println();
        System.out.println("Contains " + largestSize + " nodes.");
        for (int r = 0; r < colorGridArr.length; r++) {
            for (int c = 0; c < colorGridArr[0].length; c++) {
                String s = colorGridArr[r][c].getColorCode() + "" + colorGridArr[r][c].getChunkNumber();
                if (!s.equals(largestChunkName)) {
                    colorGridArr[r][c].setColorCode(" ");
                    colorGridArr[r][c].setChunkNumber(0);
                }
                System.out.print(colorGridArr[r][c].getColorCode() + colorGridArr[r][c].getChunkNumber() + " ");
            }
            System.out.println();
        }
    }

    static String getRandom(String[] array) {
        int rnd = random.nextInt(array.length);
        return array[rnd];
    }

}
