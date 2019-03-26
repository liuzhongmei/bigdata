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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DateDimension extends BaseDimension {
    private String year;
    private String month;
    private String day;

    @Override
    public int compareTo(BaseDimension baseDimension) {
        DateDimension another = (DateDimension) baseDimension;
        int result = this.year.compareTo(another.year);
        if(result != 0){
            return result;
        }
        result = this.month.compareTo(another.month);
        if(result != 0){
            return result;
        }
        result = this.day.compareTo(another.day);
        return 0;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeUTF(year);
        output.writeUTF(month);
        output.writeUTF(day);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.year = in.readUTF();
        this.month = in.readUTF();
        this.day = in.readUTF();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DateDimension that = (DateDimension) o;
        return Objects.equals(year, that.year) &&
                Objects.equals(month, that.month) &&
                Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }
}
