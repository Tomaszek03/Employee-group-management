package aoim.zad4;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class GroupsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private int group_id;

    @Column(name = "group_name", nullable = false)
    private String group_name;

    @Column(name = "group_capacity", nullable = false)
    private int group_capacity;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getGroup_capacity() {
        return group_capacity;
    }

    public void setGroup_capacity(int group_capacity) {
        this.group_capacity = group_capacity;
    }
}
