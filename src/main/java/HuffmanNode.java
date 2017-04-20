import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Huffman info.
 */
@Getter
@Setter
@ToString
public class HuffmanNode implements Comparable<HuffmanNode> {
    /**
     * 叶子节点总数，用于计算权值.
     */
    static int total = 0;
    private int frequence = 1;
    private float weight = 0.0f;
    private Byte byteCode;
    private HuffmanNode leftChild = null;
    private HuffmanNode rightChild = null;
    private HuffmanNode parent = null;

    public HuffmanNode() {

    }

    public HuffmanNode(float weight) {
        this.weight = weight;
    }


    @Override
    public int compareTo(HuffmanNode o) {
        if (this.getWeight() > o.getWeight())
            return 1;
        if (this.getWeight() < o.getWeight())
            return -1;
        return 0;
    }
}
