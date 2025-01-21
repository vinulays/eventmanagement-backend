package lk.earth.eventmanagement.entity;

import lk.earth.eventmanagement.util.RegexPattern;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 4)
    @Column(name = "number", length = 4)
    @Pattern(regexp = "^\\d{4}$", message = "Invalid Number")
    private String number;

    @Size(max = 150)
    @Column(name = "fullname", length = 150)
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Full name")
    private String fullname;

    @Size(max = 45)
    @Column(name = "callingname", length = 45)
    @Pattern(regexp = "^([A-Z][a-z]+)$", message = "Invalid calling name")
    private String callingname;

    @Column(name = "photo")
    private byte[] photo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @Column(name = "dobirth")
    private LocalDate dobirth;

    @Size(max = 12)
    @Column(name = "nic", length = 12)
    @Pattern(regexp = "^(([\\d]{9}[vVxX])|([\\d]{12}))$", message = "Invalid nic number")
    private String nic;

    @Lob
    @Column(name = "address")
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid address")
    private String address;

    @Size(max = 10)
    @Column(name = "mobile", length = 10)
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid mobile number")
    private String mobile;

    @Size(max = 10)
    @Column(name = "land", length = 10)
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid land line number")
    private String land;

    @Column(name = "doassignment")
    private LocalDate doassignment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "designation_id", nullable = false)
    private Designation designation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employeestatus_id", nullable = false)
    private Employeestatus employeestatus;

    @Lob
    @Column(name = "description")
    @Pattern(regexp = "^.*$", message = "Invalid description")
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCallingname() {
        return callingname;
    }

    public void setCallingname(String callingname) {
        this.callingname = callingname;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDobirth() {
        return dobirth;
    }

    public void setDobirth(LocalDate dobirth) {
        this.dobirth = dobirth;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public LocalDate getDoassignment() {
        return doassignment;
    }

    public void setDoassignment(LocalDate doassignment) {
        this.doassignment = doassignment;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Employeestatus getEmployeestatus() {
        return employeestatus;
    }

    public void setEmployeestatus(Employeestatus employeestatus) {
        this.employeestatus = employeestatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}