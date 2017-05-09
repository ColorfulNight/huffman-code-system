import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * The type Huffman info.
 */
@Setter
@Getter
@ToString
public class HuffmanInfo implements Comparable<HuffmanInfo> {
    private int times = 1;
    private float weight = 0.0f;
    private Byte code;
    private String huffmanCode;
    private HuffmanInfo leftChild = null;
    private HuffmanInfo rightChild = null;
    private HuffmanInfo parent = null;

    public HuffmanInfo(Byte code) {
        this.code = code;
    }

    public HuffmanInfo(float weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(HuffmanInfo o) {
        if (this.getWeight() > o.getWeight()) {
            return 1;
        }
        if (this.getWeight() < o.getWeight()) {
            return -1;
        }
        return 0;
    }
}
