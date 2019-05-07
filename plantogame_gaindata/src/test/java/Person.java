import java.io.Serializable;
import java.util.Arrays;

public class Person implements Serializable {
    private static String  serialVersionUID = "-8457659525256925264";
    private String name;
    private String gender;
    private Integer age;
    private String[] otherInfo;
    public Person(String name, String gender, Integer age, String[] otherInfo) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.otherInfo = otherInfo;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", otherInfo=" + Arrays.toString(otherInfo) +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String[] getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String[] otherInfo) {
        this.otherInfo = otherInfo;
    }
}
