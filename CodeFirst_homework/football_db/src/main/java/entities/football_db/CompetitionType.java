package entities.football_db;

import javax.persistence.*;

@Entity
public class CompetitionType {
    private Integer id;
    private CompetitionTypes type;
    public CompetitionType() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public CompetitionTypes getType() {
        return type;
    }

    public void setType(CompetitionTypes type) {
        this.type = type;
    }

    private enum CompetitionTypes {
        Local,
        National,
        International,
    }
}
