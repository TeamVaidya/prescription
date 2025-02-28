package com.spring.vaidya.entity;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Schema(description = "Entity representing a prescription issued to a patient")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the prescription", example = "101")
    private Long prescriptionId;

    @Schema(description = "Recorded fever temperature", example = "101.5")
    private Double fever;

    @Schema(description = "Weight of the patient", example = "75.0")
    private Double weight;

    @Schema(description = "Blood pressure measurement", example = "120/80")
    private String bp;

    @Schema(description = "Sugar level in blood", example = "110.0")
    private Double sugar;

    @Schema(description = "Date of prescription", example = "2024-09-17")
    private LocalDate date;

    @ElementCollection
    @Schema(description = "List of medical tests prescribed")
    private List<String> test;

    @ElementCollection
    @Schema(description = "List of medicines prescribed")
    private List<String> medicine;

    @ElementCollection
    @Schema(description = "Patient's medical history notes")
    private List<String> history;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @Schema(description = "Doctor who issued the prescription")
    private User user;

    @ManyToOne
    @JoinColumn(name = "slotId", nullable = false)
    @Schema(description = "Slot during which the prescription was issued")
    private Slot slot;

    @ManyToOne
    @JoinColumn(name = "patientId", nullable = false)
    @Schema(description = "Patient to whom the prescription belongs")
    private Patient patient;


	public Long getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public Double getFever() {
		return fever;
	}

	public void setFever(Double fever) {
		this.fever = fever;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getBp() {
		return bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
	}

	public Double getSugar() {
		return sugar;
	}

	public void setSugar(Double sugar) {
		this.sugar = sugar;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<String> getTest() {
		return test;
	}

	public void setTest(List<String> test) {
		this.test = test;
	}

	public List<String> getMedicine() {
		return medicine;
	}

	public void setMedicine(List<String> medicine) {
		this.medicine = medicine;
	}

	public List<String> getHistory() {
		return history;
	}

	public void setHistory(List<String> history) {
		this.history = history;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
    
    

}
