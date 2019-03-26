package kv.key;

import kv.base.BaseDimension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * @author: lzm
 * @date: 2019/3/19
 * @description:
 * @version: 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDimension extends BaseDimension {
    private String telephone;
    private String name;

    @Override
    public int compareTo(BaseDimension baseDimension) {
        ContactDimension another = (ContactDimension) baseDimension;
        int result = this.name.compareTo(another.name);
        if(result != 0){
            return result;
        }
        result = this.telephone.compareTo(another.telephone);
        return result;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeUTF(telephone);
        output.writeUTF(name);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.telephone = in.readUTF();
        this.name = in.readUTF();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContactDimension that = (ContactDimension) o;
        return Objects.equals(telephone, that.telephone) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telephone, name);
    }
}
