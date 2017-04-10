import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Huffman info.
 */
@Setter
@Getter
@ToString
public class HuffmanInfo {
    /**
     * 叶子节点总数，用于计算权值.
     */
    static int total = 0;
    private int times = 1;
    private float weight = 0.0f;
    private String code;
    private StringBuilder huffmanCode;
    private String leftChild = null;
    private String rightChild = null;
    private String parent = null;

    public HuffmanInfo() {
        total++;
    }

    public HuffmanInfo(String code) {
        this.code = code;
        total++;
    }

    public HuffmanInfo(float weight, String code) {
        this.weight = weight;
        this.code = code;
        total++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HuffmanInfo that = (HuffmanInfo) o;

        if (times != that.times) return false;
        if (Float.compare(that.weight, weight) != 0) return false;
        if (!code.equals(that.code)) return false;
        if (huffmanCode != null ? !huffmanCode.toString().equals(that.huffmanCode.toString()) : that.huffmanCode != null) return false;
        if (leftChild != null ? !leftChild.equals(that.leftChild) : that.leftChild != null) return false;
        if (rightChild != null ? !rightChild.equals(that.rightChild) : that.rightChild != null) return false;
        return parent != null ? parent.equals(that.parent) : that.parent == null;
    }

    @Override
    public int hashCode() {
        int result = times;
        result = 31 * result + (weight != +0.0f ? Float.floatToIntBits(weight) : 0);
        result = 31 * result + code.hashCode();
        result = 31 * result + (huffmanCode != null ? huffmanCode.hashCode() : 0);
        result = 31 * result + (leftChild != null ? leftChild.hashCode() : 0);
        result = 31 * result + (rightChild != null ? rightChild.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }

    /**
     * 测试用
     */
    @Deprecated
    public HuffmanInfo(float weight, StringBuilder huffmanCode) {
        this.weight = weight;
        this.huffmanCode = huffmanCode;
    }

    /**
     * 测试用
     */
    @Deprecated
    public HuffmanInfo(int times, float weight, String code, StringBuilder huffmanCode, String leftChild, String rightChild, String parent) {
        this.times = times;
        this.weight = weight;
        this.code = code;
        this.huffmanCode = huffmanCode;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.parent = parent;
    }
}
