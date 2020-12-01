import java.util.Objects;

public class Node {
    private int x;
    private int y;
    private String colorCode;
    private int chunkNumber;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public Node(int x, int y, String colorCode) {
        this.x = x;
        this.y = y;
        this.colorCode = colorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x &&
                y == node.y &&
                chunkNumber == node.chunkNumber &&
                Objects.equals(colorCode, node.colorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, colorCode, chunkNumber);
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", colorCode='" + colorCode + '\'' +
                ", chunkNumber=" + chunkNumber +
                '}';
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(int chunkNumber) {
        this.chunkNumber = chunkNumber;
    }
}
