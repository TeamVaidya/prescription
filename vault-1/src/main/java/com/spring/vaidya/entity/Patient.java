package com.spring.vaidya.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

@Entity
@Schema(name = "Patient", description = "Details of a Patient entity")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the patient", example = "1", accessMode = AccessMode.READ_ONLY)
    private Long patientId;

    @Schema(description = "Full name of the patient", example = "John Doe", requiredMode = RequiredMode.REQUIRED)
    private String patientName;

    @Schema(description = "Mobile number of the patient", example = "9876543210", requiredMode = RequiredMode.REQUIRED)
    private String mobileNo;

    @Schema(description = "Email ID of the patient", example = "johndoe@example.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Aadhar number of the patient", example = "123456789012", requiredMode = RequiredMode.REQUIRED)
    private Long aadharNo;

    @Schema(description = "Age of the patient", example = "30", requiredMode = RequiredMode.REQUIRED)
    private Integer age;

    @Schema(description = "Registration date and time", example = "2024-02-24T15:30:00", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime dateTime;

    @Schema(description = "Address of the patient", example = "123 Main Street, City", requiredMode = RequiredMode.REQUIRED)
    private String address;

    @Schema(description = "Role ID associated with the patient", example = "2", requiredMode = RequiredMode.REQUIRED)
    private Integer roleId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @Schema(description = "User associated with the patient", requiredMode = RequiredMode.REQUIRED)
    private User user;

    @ManyToOne
    @JoinColumn(name = "slotId")
    @Schema(description = "Slot assigned to the patient", requiredMode = RequiredMode.REQUIRED)
    private Slot slot;

    public Patient() {
    }

    public Patient(Long patientId, String patientName, String mobileNo, String email, Long aadharNo, Integer age,
                   LocalDateTime dateTime, String address, Integer roleId, User user, Slot slot) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.mobileNo = mobileNo;
        this.email = email;
        this.aadharNo = aadharNo;
        this.age = age;
        this.dateTime = dateTime;
        this.address = address;
        this.roleId = roleId;
        this.user = user;
        this.slot = slot;
    }

    // Getters and Setters
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getAadharNo() { return aadharNo; }
    public void setAadharNo(Long aadharNo) { this.aadharNo = aadharNo; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Slot getSlot() { return slot; }
    public void setSlot(Slot slot) { this.slot = slot; }

    @Override
    public int hashCode() {
        return Objects.hash(aadharNo, address, age, dateTime, user, email, mobileNo, patientId, patientName, roleId, slot);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patient other = (Patient) obj;
        return Objects.equals(aadharNo, other.aadharNo) &&
                Objects.equals(address, other.address) &&
                Objects.equals(age, other.age) &&
                Objects.equals(dateTime, other.dateTime) &&
                Objects.equals(user, other.user) &&
                Objects.equals(email, other.email) &&
                Objects.equals(mobileNo, other.mobileNo) &&
                Objects.equals(patientId, other.patientId) &&
                Objects.equals(patientName, other.patientName) &&
                Objects.equals(roleId, other.roleId) &&
                Objects.equals(slot, other.slot);
    }

    @Override
    public String toString() {
        return "Patient [patientId=" + patientId + ", patientName=" + patientName + ", mobileNo=" + mobileNo
                + ", email=" + email + ", aadharNo=" + aadharNo + ", age=" + age + ", dateTime=" + dateTime
                + ", address=" + address + ", roleId=" + roleId + ", user=" + user + ", slot=" + slot + "]";
    }
}
