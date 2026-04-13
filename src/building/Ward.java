package building;

import model.Doctor;
import model.Nurse;
import model.Patient;

/**
 * Represents a single Ward within a Room.
 * Each ward can hold at most one doctor, one nurse, and one patient.
 */
public class Ward {

    private final int wardNumber;
    private Doctor  assignedDoctor;
    private Nurse   assignedNurse;
    private Patient assignedPatient;

    public Ward(int wardNumber) {
        this.wardNumber = wardNumber;
    }

    // ── Assignment methods ─────────────────────────────────────────────────────

    public void assignDoctor(Doctor doctor)   { this.assignedDoctor  = doctor; }
    public void assignNurse(Nurse nurse)      { this.assignedNurse   = nurse; }
    public void assignPatient(Patient patient){ this.assignedPatient = patient; }

    public void removeDoctor()  { this.assignedDoctor  = null; }
    public void removeNurse()   { this.assignedNurse   = null; }
    public void removePatient() { this.assignedPatient = null; }

    // ── Getters ────────────────────────────────────────────────────────────────

    public int     getWardNumber()    { return wardNumber; }
    public Doctor  getAssignedDoctor()  { return assignedDoctor; }
    public Nurse   getAssignedNurse()   { return assignedNurse; }
    public Patient getAssignedPatient() { return assignedPatient; }

    @Override
    public String toString() {
        String doc     = (assignedDoctor  != null) ? assignedDoctor.getName()  : "Unassigned";
        String nurse   = (assignedNurse   != null) ? assignedNurse.getName()   : "Unassigned";
        String patient = (assignedPatient != null) ? assignedPatient.getName() : "Unassigned";
        return String.format("  Ward %-3d | Doctor: %-18s | Nurse: %-18s | Patient: %s",
                wardNumber, doc, nurse, patient);
    }
}
