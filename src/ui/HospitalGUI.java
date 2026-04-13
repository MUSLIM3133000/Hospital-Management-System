package ui;

import model.Address;
import model.Doctor;
import model.Nurse;
import model.Patient;
import service.HospitalService;
import service.ManagementSystem;
import util.Validator;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main GUI window for the Hospital Management System.
 *
 * Architecture: MVC
 *  - Model      : ManagementSystem (service layer behind HospitalService interface)
 *  - View       : this class (renders all panels, tables, dialogs)
 *  - Controller : ActionListeners + helper methods wired inside this class
 *
 * Compatible with Java 11+.
 */
public class HospitalGUI extends JFrame {

    // ── Design tokens ──────────────────────────────────────────────────────────
    private static final Color PRIMARY       = new Color(0x1A73E8);
    private static final Color PRIMARY_DARK  = new Color(0x1558B0);
    private static final Color ACCENT        = new Color(0x34A853);
    private static final Color DANGER        = new Color(0xEA4335);
    private static final Color BG_DARK       = new Color(0x1E2A3A);
    private static final Color BG_CARD       = new Color(0x253347);
    private static final Color BG_PANEL      = new Color(0x2D3F55);
    private static final Color TEXT_LIGHT    = new Color(0xECF0F1);
    private static final Color TEXT_MUTED    = new Color(0x95A5A6);
    private static final Color TABLE_HEADER  = new Color(0x1A73E8);
    private static final Color TABLE_ROW_ALT = new Color(0x2A3D52);

    private static final Font FONT_TITLE    = new Font("Segoe UI", Font.BOLD,  22);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD,  14);
    private static final Font FONT_NORMAL   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL    = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BUTTON   = new Font("Segoe UI", Font.BOLD,  12);

    // ── Backend (injected through interface — testable / swappable) ────────────
    private final HospitalService service = new ManagementSystem();

    // ── Layout ─────────────────────────────────────────────────────────────────
    private JPanel     contentPanel;
    private CardLayout cardLayout;

    // ── Table models (kept as fields so refresh methods can reach them) ────────
    private DefaultTableModel doctorTableModel;
    private DefaultTableModel nurseTableModel;
    private DefaultTableModel patientTableModel;
    private DefaultTableModel apptTableModel;

    // ── Building output ────────────────────────────────────────────────────────
    private JTextArea buildingTextArea;

    // ══════════════════════════════════════════════════════════════════════════
    //  Constructor
    // ══════════════════════════════════════════════════════════════════════════

    public HospitalGUI() {
        initWindow();
        buildLayout();
        setVisible(true);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Window Setup
    // ══════════════════════════════════════════════════════════════════════════

    private void initWindow() {
        setTitle("Paradise Hospital — Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
    }

    private void buildLayout() {
        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);

        cardLayout   = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BG_DARK);

        contentPanel.add(buildDashboard(),        "DASHBOARD");
        contentPanel.add(buildDoctorPanel(),      "DOCTORS");
        contentPanel.add(buildNursePanel(),       "NURSES");
        contentPanel.add(buildPatientPanel(),     "PATIENTS");
        contentPanel.add(buildAppointmentPanel(), "APPOINTMENTS");
        contentPanel.add(buildBuildingPanel(),    "BUILDING");

        add(contentPanel, BorderLayout.CENTER);
        cardLayout.show(contentPanel, "DASHBOARD");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Sidebar Navigation
    // ══════════════════════════════════════════════════════════════════════════

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(BG_CARD);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(210, 0));

        // ── Logo block ─────────────────────────────────────────────────────────
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(PRIMARY_DARK);
        logoPanel.setMaximumSize(new Dimension(210, 80));
        logoPanel.setPreferredSize(new Dimension(210, 80));
        logoPanel.setBorder(new EmptyBorder(12, 18, 12, 18));

        JLabel logo = new JLabel("  Hospital MS");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        logo.setForeground(Color.WHITE);

        JLabel sub = new JLabel("  Paradise Hospital");
        sub.setFont(FONT_SMALL);
        sub.setForeground(new Color(0xBDC3C7));

        JPanel lt = new JPanel();
        lt.setLayout(new BoxLayout(lt, BoxLayout.Y_AXIS));
        lt.setOpaque(false);
        lt.add(logo);
        lt.add(Box.createVerticalStrut(4));
        lt.add(sub);
        logoPanel.add(lt, BorderLayout.CENTER);

        sidebar.add(logoPanel);
        sidebar.add(Box.createVerticalStrut(16));

        // ── Nav items ──────────────────────────────────────────────────────────
        addNavItem(sidebar, "  Home  /  Dashboard", "DASHBOARD");
        addNavItem(sidebar, "  Doctors",             "DOCTORS");
        addNavItem(sidebar, "  Nurses",              "NURSES");
        addNavItem(sidebar, "  Patients",            "PATIENTS");
        addNavItem(sidebar, "  Appointments",        "APPOINTMENTS");
        addNavItem(sidebar, "  Building & Wards",    "BUILDING");

        sidebar.add(Box.createVerticalGlue());

        // ── Exit button ────────────────────────────────────────────────────────
        JButton exitBtn = styledButton("  Exit", DANGER);
        exitBtn.setMaximumSize(new Dimension(190, 40));
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?", "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) System.exit(0);
        });
        sidebar.add(exitBtn);
        sidebar.add(Box.createVerticalStrut(16));
        return sidebar;
    }

    /** Creates and adds a single navigation button to the sidebar. */
    private void addNavItem(JPanel sidebar, String label, String card) {
        JButton btn = new JButton(label);
        btn.setFont(FONT_NORMAL);
        btn.setForeground(TEXT_LIGHT);
        btn.setBackground(BG_CARD);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(10, 20, 10, 10));
        btn.setMaximumSize(new Dimension(210, 44));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(BG_PANEL); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(BG_CARD);  }
        });
        btn.addActionListener(e -> cardLayout.show(contentPanel, card));
        sidebar.add(btn);
        sidebar.add(Box.createVerticalStrut(3));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Dashboard
    // ══════════════════════════════════════════════════════════════════════════

    private JPanel buildDashboard() {
        JPanel panel = darkPanel(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.add(sectionTitle("Dashboard  \u2014  Overview"), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 3, 18, 18));
        cards.setOpaque(false);

        cards.add(liveStatCard("Doctors",      "doctors",  PRIMARY));
        cards.add(liveStatCard("Nurses",       "nurses",   ACCENT));
        cards.add(liveStatCard("Patients",     "patients", new Color(0xF4B942)));
        cards.add(liveStatCard("Appointments", "appts",    new Color(0x9B59B6)));
        cards.add(infoCard("Hospital", "Paradise Hospital\nDhaka, Bangladesh", new Color(0x16A085)));
        cards.add(infoCard("System",   "Status: Online\nVersion: 2.0",         new Color(0x2980B9)));

        panel.add(cards, BorderLayout.CENTER);

        JLabel footer = new JLabel(
            "Paradise Hospital Management System  \u2022  Java Swing  \u2022  v2.0",
            SwingConstants.CENTER);
        footer.setFont(FONT_SMALL);
        footer.setForeground(TEXT_MUTED);
        panel.add(footer, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Creates a stat card on the dashboard that auto-refreshes its count
     * every 1.5 seconds using a Swing Timer.
     *
     * @param label   Display label shown above the number
     * @param dataKey One of: "doctors", "nurses", "patients", "appts"
     * @param accent  Colour used for the large count number
     */
    private JPanel liveStatCard(String label, String dataKey, Color accent) {
        JPanel card = roundedCard();
        card.setLayout(new BorderLayout(8, 8));
        card.setBorder(new EmptyBorder(20, 22, 20, 22));

        JLabel titleLbl = new JLabel(label);
        titleLbl.setFont(FONT_SUBTITLE);
        titleLbl.setForeground(TEXT_LIGHT);

        JLabel countLbl = new JLabel("0");
        countLbl.setFont(new Font("Segoe UI", Font.BOLD, 38));
        countLbl.setForeground(accent);

        card.add(titleLbl, BorderLayout.NORTH);
        card.add(countLbl, BorderLayout.CENTER);

        // Refresh the count on a background timer
        new Timer(1500, e -> {
            int count;
            switch (dataKey) {
                case "doctors":  count = service.getAllDoctors().size();  break;
                case "nurses":   count = service.getAllNurses().size();   break;
                case "patients": count = service.getAllPatients().size(); break;
                case "appts":
                    count = (int) service.getAppointmentSummary().stream()
                                         .filter(s -> !s.startsWith("No")).count();
                    break;
                default:         count = 0; break;
            }
            countLbl.setText(String.valueOf(count));
        }).start();

        return card;
    }

    /** Non-live informational card for static content on the dashboard. */
    private JPanel infoCard(String title, String body, Color accent) {
        JPanel card = roundedCard();
        card.setLayout(new BorderLayout(8, 8));
        card.setBorder(new EmptyBorder(20, 22, 20, 22));

        JLabel lbl = new JLabel(title);
        lbl.setFont(FONT_SUBTITLE);
        lbl.setForeground(TEXT_LIGHT);

        JTextArea area = new JTextArea(body);
        area.setFont(FONT_NORMAL);
        area.setForeground(accent);
        area.setOpaque(false);
        area.setEditable(false);

        card.add(lbl,  BorderLayout.NORTH);
        card.add(area, BorderLayout.CENTER);
        return card;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Doctor Panel
    // ══════════════════════════════════════════════════════════════════════════

    private JPanel buildDoctorPanel() {
        JPanel panel = darkPanel(new BorderLayout(0, 14));
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));
        panel.add(sectionTitle("Doctor Management"), BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Phone", "Address", "Max Capacity", "Assigned Patients"};
        doctorTableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(doctorTableModel);
        JScrollPane scroll = new JScrollPane(table);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);

        JButton addBtn     = styledButton("Add Doctor",   PRIMARY);
        JButton removeBtn  = styledButton("Remove",       DANGER);
        JButton viewBtn    = styledButton("View Details", BG_PANEL);
        JButton refreshBtn = styledButton("Refresh",      BG_PANEL);

        addBtn.addActionListener(e -> showAddDoctorDialog());
        removeBtn.addActionListener(e -> removeSelected(table, doctorTableModel, "doctor"));
        viewBtn.addActionListener(e -> viewDoctorDetails(table));
        refreshBtn.addActionListener(e -> refreshDoctorTable());

        buttons.add(addBtn);
        buttons.add(removeBtn);
        buttons.add(viewBtn);
        buttons.add(refreshBtn);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private void showAddDoctorDialog() {
        JTextField[] f = newFields(7);
        String[] labels = {
            "Doctor ID", "Full Name", "Phone",
            "ZIP Code", "City", "Street",
            "Max Patients (1-100)"
        };
        if (showFormDialog("Add New Doctor", f, labels) != JOptionPane.OK_OPTION) return;

        String id     = f[0].getText().trim();
        String name   = f[1].getText().trim();
        String phone  = f[2].getText().trim();
        String zip    = f[3].getText().trim();
        String city   = f[4].getText().trim();
        String street = f[5].getText().trim();
        String capStr = f[6].getText().trim();

        if (!Validator.allNonEmpty(id, name, phone, zip, city, street, capStr)) {
            showError("All fields are required."); return;
        }
        if (!Validator.isValidId(id)) {
            showError("ID must be 2\u201320 alphanumeric characters."); return;
        }
        if (!Validator.isValidPhone(phone)) {
            showError("Invalid phone number."); return;
        }
        int cap = Validator.parsePositiveInt(capStr, 1, 100);
        if (cap == -1) {
            showError("Capacity must be a number between 1 and 100."); return;
        }

        if (service.addDoctor(id, name, phone, new Address(zip, city, street), cap)) {
            showSuccess("Doctor \"" + name + "\" added successfully!");
            refreshDoctorTable();
        } else {
            showError("A doctor with ID \"" + id + "\" already exists.");
        }
    }

    private void refreshDoctorTable() {
        doctorTableModel.setRowCount(0);
        for (Doctor d : service.getAllDoctors()) {
            doctorTableModel.addRow(new Object[]{
                d.getId(), d.getName(), d.getPhone(),
                d.getAddress(), d.getMaxPatientCapacity(),
                d.getCurrentPatientCount()
            });
        }
    }

    private void viewDoctorDetails(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) { showError("Please select a doctor row first."); return; }

        String id = (String) doctorTableModel.getValueAt(row, 0);
        Doctor d  = service.findDoctor(id);
        if (d == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append(d).append("\n\nAssigned Patients:\n");
        if (d.getAssignedPatientIds().isEmpty()) {
            sb.append("  (none assigned)");
        } else {
            for (String pid : d.getAssignedPatientIds()) {
                Patient p = service.findPatient(pid);
                String pName = (p != null) ? p.getName() : "Unknown";
                sb.append("  \u2022 ").append(pName).append("  (ID: ").append(pid).append(")\n");
            }
        }
        showInfo("Doctor Details \u2014 " + d.getName(), sb.toString());
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Nurse Panel
    // ══════════════════════════════════════════════════════════════════════════

    private JPanel buildNursePanel() {
        JPanel panel = darkPanel(new BorderLayout(0, 14));
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));
        panel.add(sectionTitle("Nurse Management"), BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Phone", "Address", "Ward Capacity", "Assigned Wards"};
        nurseTableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(nurseTableModel);
        JScrollPane scroll = new JScrollPane(table);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);

        JButton addBtn     = styledButton("Add Nurse", PRIMARY);
        JButton removeBtn  = styledButton("Remove",    DANGER);
        JButton refreshBtn = styledButton("Refresh",   BG_PANEL);

        addBtn.addActionListener(e -> showAddNurseDialog());
        removeBtn.addActionListener(e -> removeSelected(table, nurseTableModel, "nurse"));
        refreshBtn.addActionListener(e -> refreshNurseTable());

        buttons.add(addBtn);
        buttons.add(removeBtn);
        buttons.add(refreshBtn);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private void showAddNurseDialog() {
        JTextField[] f = newFields(7);
        String[] labels = {
            "Nurse ID", "Full Name", "Phone",
            "ZIP Code", "City", "Street",
            "Max Wards (1-20)"
        };
        if (showFormDialog("Add New Nurse", f, labels) != JOptionPane.OK_OPTION) return;

        String id     = f[0].getText().trim();
        String name   = f[1].getText().trim();
        String phone  = f[2].getText().trim();
        String zip    = f[3].getText().trim();
        String city   = f[4].getText().trim();
        String street = f[5].getText().trim();
        String capStr = f[6].getText().trim();

        if (!Validator.allNonEmpty(id, name, phone, zip, city, street, capStr)) {
            showError("All fields are required."); return;
        }
        if (!Validator.isValidId(id)) {
            showError("ID must be 2\u201320 alphanumeric characters."); return;
        }
        if (!Validator.isValidPhone(phone)) {
            showError("Invalid phone number."); return;
        }
        int cap = Validator.parsePositiveInt(capStr, 1, 20);
        if (cap == -1) {
            showError("Ward capacity must be a number between 1 and 20."); return;
        }

        if (service.addNurse(id, name, phone, new Address(zip, city, street), cap)) {
            showSuccess("Nurse \"" + name + "\" added successfully!");
            refreshNurseTable();
        } else {
            showError("A nurse with ID \"" + id + "\" already exists.");
        }
    }

    private void refreshNurseTable() {
        nurseTableModel.setRowCount(0);
        for (Nurse n : service.getAllNurses()) {
            nurseTableModel.addRow(new Object[]{
                n.getId(), n.getName(), n.getPhone(),
                n.getAddress(), n.getMaxWardCapacity(),
                n.getCurrentWardCount()
            });
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Patient Panel
    // ══════════════════════════════════════════════════════════════════════════

    private JPanel buildPatientPanel() {
        JPanel panel = darkPanel(new BorderLayout(0, 14));
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));
        panel.add(sectionTitle("Patient Management"), BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Phone", "Address", "Assigned Ward"};
        patientTableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(patientTableModel);
        JScrollPane scroll = new JScrollPane(table);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);

        JButton addBtn     = styledButton("Add Patient", PRIMARY);
        JButton removeBtn  = styledButton("Remove",      DANGER);
        JButton refreshBtn = styledButton("Refresh",     BG_PANEL);

        addBtn.addActionListener(e -> showAddPatientDialog());
        removeBtn.addActionListener(e -> removeSelected(table, patientTableModel, "patient"));
        refreshBtn.addActionListener(e -> refreshPatientTable());

        buttons.add(addBtn);
        buttons.add(removeBtn);
        buttons.add(refreshBtn);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private void showAddPatientDialog() {
        JTextField[] f = newFields(7);
        String[] labels = {
            "Patient ID", "Full Name", "Phone",
            "ZIP Code", "City", "Street",
            "Ward Number"
        };
        if (showFormDialog("Add New Patient", f, labels) != JOptionPane.OK_OPTION) return;

        String id     = f[0].getText().trim();
        String name   = f[1].getText().trim();
        String phone  = f[2].getText().trim();
        String zip    = f[3].getText().trim();
        String city   = f[4].getText().trim();
        String street = f[5].getText().trim();
        String ward   = f[6].getText().trim();

        if (!Validator.allNonEmpty(id, name, phone, zip, city, street, ward)) {
            showError("All fields are required."); return;
        }
        if (!Validator.isValidId(id)) {
            showError("ID must be 2\u201320 alphanumeric characters."); return;
        }
        if (!Validator.isValidPhone(phone)) {
            showError("Invalid phone number."); return;
        }

        if (service.addPatient(id, name, phone, new Address(zip, city, street), ward)) {
            showSuccess("Patient \"" + name + "\" added successfully!");
            refreshPatientTable();
        } else {
            showError("A patient with ID \"" + id + "\" already exists.");
        }
    }

    private void refreshPatientTable() {
        patientTableModel.setRowCount(0);
        for (Patient p : service.getAllPatients()) {
            patientTableModel.addRow(new Object[]{
                p.getId(), p.getName(), p.getPhone(),
                p.getAddress(), p.getAssignedWardNumber()
            });
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Appointment Panel
    // ══════════════════════════════════════════════════════════════════════════

    private JPanel buildAppointmentPanel() {
        JPanel panel = darkPanel(new BorderLayout(0, 14));
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));
        panel.add(sectionTitle("Appointment Management"), BorderLayout.NORTH);

        String[] cols = {"Doctor ID", "Doctor Name", "Patient ID", "Patient Name"};
        apptTableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = styledTable(apptTableModel);
        JScrollPane scroll = new JScrollPane(table);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);

        JButton bookBtn    = styledButton("Book Appointment", PRIMARY);
        JButton cancelBtn  = styledButton("Cancel",           DANGER);
        JButton refreshBtn = styledButton("Refresh",          BG_PANEL);

        bookBtn.addActionListener(e -> showBookAppointmentDialog());
        cancelBtn.addActionListener(e -> cancelSelectedAppointment(table));
        refreshBtn.addActionListener(e -> refreshAppointmentTable());

        buttons.add(bookBtn);
        buttons.add(cancelBtn);
        buttons.add(refreshBtn);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private void showBookAppointmentDialog() {
        JTextField doctorField  = styledField();
        JTextField patientField = styledField();

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBackground(BG_CARD);
        form.setBorder(new EmptyBorder(12, 16, 12, 16));
        form.add(formLabel("Doctor ID:"));  form.add(doctorField);
        form.add(formLabel("Patient ID:")); form.add(patientField);

        int opt = JOptionPane.showConfirmDialog(this, form, "Book Appointment",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opt != JOptionPane.OK_OPTION) return;

        String dId = doctorField.getText().trim();
        String pId = patientField.getText().trim();

        if (!Validator.allNonEmpty(dId, pId)) {
            showError("Both Doctor ID and Patient ID are required."); return;
        }
        if (service.findDoctor(dId) == null) {
            showError("Doctor ID \"" + dId + "\" not found."); return;
        }
        if (service.findPatient(pId) == null) {
            showError("Patient ID \"" + pId + "\" not found."); return;
        }

        if (service.bookAppointment(dId, pId)) {
            showSuccess("Appointment booked successfully!");
            refreshAppointmentTable();
        } else {
            showError("Could not book appointment.\nThe doctor may be at full capacity or\nthis patient is already assigned to them.");
        }
    }

    private void cancelSelectedAppointment(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) { showError("Please select an appointment row to cancel."); return; }

        String dId    = (String) apptTableModel.getValueAt(row, 0);
        String dName  = (String) apptTableModel.getValueAt(row, 1);
        String pId    = (String) apptTableModel.getValueAt(row, 2);
        String pName  = (String) apptTableModel.getValueAt(row, 3);

        int c = JOptionPane.showConfirmDialog(this,
                "Cancel appointment:\n  Dr. " + dName + "  \u2192  " + pName + "?",
                "Confirm Cancel", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) {
            service.cancelAppointment(dId, pId);
            showSuccess("Appointment cancelled.");
            refreshAppointmentTable();
        }
    }

    private void refreshAppointmentTable() {
        apptTableModel.setRowCount(0);
        for (Doctor d : service.getAllDoctors()) {
            for (String pid : d.getAssignedPatientIds()) {
                Patient p = service.findPatient(pid);
                if (p != null) {
                    apptTableModel.addRow(new Object[]{
                        d.getId(), d.getName(), p.getId(), p.getName()
                    });
                }
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Building Panel
    // ══════════════════════════════════════════════════════════════════════════

    private JPanel buildBuildingPanel() {
        JPanel panel = darkPanel(new BorderLayout(0, 14));
        panel.setBorder(new EmptyBorder(24, 28, 24, 28));
        panel.add(sectionTitle("Building & Ward Management"), BorderLayout.NORTH);

        buildingTextArea = new JTextArea(
            "Building not initialised.\nClick \"Setup Building\" to create rooms and wards.");
        buildingTextArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        buildingTextArea.setBackground(BG_CARD);
        buildingTextArea.setForeground(TEXT_LIGHT);
        buildingTextArea.setEditable(false);
        buildingTextArea.setLineWrap(false);
        buildingTextArea.setBorder(new EmptyBorder(14, 16, 14, 16));

        JScrollPane scroll = new JScrollPane(buildingTextArea);
        styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);

        JButton setupBtn    = styledButton("Setup Building", PRIMARY);
        JButton assignDrBtn = styledButton("Assign Doctor",  ACCENT);
        JButton assignNrBtn = styledButton("Assign Nurse",   new Color(0xF39C12));
        JButton assignPtBtn = styledButton("Assign Patient", new Color(0x9B59B6));
        JButton refreshBtn  = styledButton("Refresh View",   BG_PANEL);

        setupBtn.addActionListener(e -> showSetupBuildingDialog());
        assignDrBtn.addActionListener(e -> showAssignToWardDialog("doctor"));
        assignNrBtn.addActionListener(e -> showAssignToWardDialog("nurse"));
        assignPtBtn.addActionListener(e -> showAssignToWardDialog("patient"));
        refreshBtn.addActionListener(e -> refreshBuildingView());

        buttons.add(setupBtn);
        buttons.add(assignDrBtn);
        buttons.add(assignNrBtn);
        buttons.add(assignPtBtn);
        buttons.add(refreshBtn);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private void showSetupBuildingDialog() {
        JTextField nameField  = styledField(); nameField.setText("Paradise Hospital");
        JTextField roomsField = styledField(); roomsField.setText("3");
        JTextField wardsField = styledField(); wardsField.setText("4");

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBackground(BG_CARD);
        form.setBorder(new EmptyBorder(12, 16, 12, 16));
        form.add(formLabel("Building Name:"));   form.add(nameField);
        form.add(formLabel("Number of Rooms:")); form.add(roomsField);
        form.add(formLabel("Wards per Room:"));  form.add(wardsField);

        int opt = JOptionPane.showConfirmDialog(this, form,
                "Setup Building", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opt != JOptionPane.OK_OPTION) return;

        String name  = nameField.getText().trim();
        int rooms    = Validator.parsePositiveInt(roomsField.getText(), 1, 20);
        int wards    = Validator.parsePositiveInt(wardsField.getText(), 1, 20);

        if (!Validator.isNonEmpty(name))  { showError("Building name is required."); return; }
        if (rooms == -1) { showError("Number of rooms must be between 1 and 20."); return; }
        if (wards == -1) { showError("Wards per room must be between 1 and 20."); return; }

        service.initBuilding(name, rooms, wards);
        showSuccess("Building \"" + name + "\" created:\n" + rooms + " rooms  \u00d7  " + wards + " wards each.");
        refreshBuildingView();
    }

    private void showAssignToWardDialog(String role) {
        JTextField roomField = styledField();
        JTextField wardField = styledField();
        JTextField idField   = styledField();

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBackground(BG_CARD);
        form.setBorder(new EmptyBorder(12, 16, 12, 16));
        form.add(formLabel("Room Number:"));              form.add(roomField);
        form.add(formLabel("Ward Number:"));              form.add(wardField);
        form.add(formLabel(capitalize(role) + " ID:"));  form.add(idField);

        String dialogTitle = "Assign " + capitalize(role) + " to Ward";
        int opt = JOptionPane.showConfirmDialog(this, form, dialogTitle,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opt != JOptionPane.OK_OPTION) return;

        int    room = Validator.parsePositiveInt(roomField.getText(), 1, 50);
        int    ward = Validator.parsePositiveInt(wardField.getText(), 1, 50);
        String id   = idField.getText().trim();

        if (room == -1 || ward == -1) {
            showError("Room and ward numbers must be positive integers."); return;
        }
        if (!Validator.isNonEmpty(id)) {
            showError(capitalize(role) + " ID is required."); return;
        }

        boolean success;
        if      ("doctor".equals(role))  success = service.assignDoctorToWard(room, ward, id);
        else if ("nurse".equals(role))   success = service.assignNurseToWard(room, ward, id);
        else                             success = service.assignPatientToWard(room, ward, id);

        if (success) {
            showSuccess(capitalize(role) + " (ID: " + id + ") assigned to Room " + room + ", Ward " + ward + ".");
            refreshBuildingView();
        } else {
            showError("Assignment failed.\n\nMake sure:\n"
                    + "  \u2022 The building has been set up\n"
                    + "  \u2022 Room " + room + " and Ward " + ward + " exist\n"
                    + "  \u2022 " + capitalize(role) + " ID \"" + id + "\" is registered");
        }
    }

    private void refreshBuildingView() {
        buildingTextArea.setText(service.getBuildingSummary());
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Shared Remove Helper
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Handles remove-button clicks for all three entity types.
     * Reads the ID from column 0 of the selected row, confirms with the user,
     * calls the appropriate service method, then refreshes the table.
     */
    private void removeSelected(JTable table, DefaultTableModel model, String entityType) {
        int row = table.getSelectedRow();
        if (row == -1) {
            showError("Please select a row to remove."); return;
        }
        String id = (String) model.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Remove " + capitalize(entityType) + " with ID: \"" + id + "\"?\nThis action cannot be undone.",
                "Confirm Remove", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        boolean removed;
        if      ("doctor".equals(entityType))  removed = service.removeDoctor(id);
        else if ("nurse".equals(entityType))   removed = service.removeNurse(id);
        else                                   removed = service.removePatient(id);

        if (removed) {
            showSuccess(capitalize(entityType) + " removed successfully.");
            if      ("doctor".equals(entityType))  refreshDoctorTable();
            else if ("nurse".equals(entityType))   refreshNurseTable();
            else                                   refreshPatientTable();
        } else {
            showError("Could not remove — " + entityType + " with ID \"" + id + "\" not found.");
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Form Dialog Factory
    // ══════════════════════════════════════════════════════════════════════════

    /** Allocates an array of pre-styled text fields. */
    private JTextField[] newFields(int count) {
        JTextField[] fields = new JTextField[count];
        for (int i = 0; i < count; i++) fields[i] = styledField();
        return fields;
    }

    /**
     * Builds a GridLayout form from parallel arrays of fields and labels,
     * shows it in a JOptionPane, and returns the user's choice constant.
     */
    private int showFormDialog(String title, JTextField[] fields, String[] labels) {
        JPanel form = new JPanel(new GridLayout(fields.length, 2, 10, 8));
        form.setBackground(BG_CARD);
        form.setBorder(new EmptyBorder(12, 16, 12, 16));
        for (int i = 0; i < fields.length; i++) {
            form.add(formLabel(labels[i] + ":"));
            form.add(fields[i]);
        }
        return JOptionPane.showConfirmDialog(this, form, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  UI Component Factories
    // ══════════════════════════════════════════════════════════════════════════

    /** Creates a JPanel with the dark background colour. */
    private JPanel darkPanel(LayoutManager layout) {
        JPanel p = new JPanel(layout);
        p.setBackground(BG_DARK);
        return p;
    }

    /**
     * Creates a card panel with rounded corners.
     * Uses an anonymous subclass that overrides paintComponent so we can
     * fill a RoundRect before Swing paints children.
     */
    private JPanel roundedCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        return card;
    }

    /** Section heading label used at the top of each content panel. */
    private JLabel sectionTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_LIGHT);
        lbl.setBorder(new EmptyBorder(0, 0, 14, 0));
        return lbl;
    }

    /** Right-side label inside a form grid. */
    private JLabel formLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_NORMAL);
        lbl.setForeground(TEXT_LIGHT);
        return lbl;
    }

    /** Pre-styled dark text field that matches the app theme. */
    private JTextField styledField() {
        JTextField f = new JTextField(18);
        f.setBackground(BG_PANEL);
        f.setForeground(TEXT_LIGHT);
        f.setCaretColor(TEXT_LIGHT);
        f.setFont(FONT_NORMAL);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 1),
                new EmptyBorder(4, 8, 4, 8)));
        return f;
    }

    /**
     * Creates a button styled for the dark theme with hover effect.
     *
     * @param text Button label
     * @param bg   Base background colour (hover uses bg.darker())
     */
    private JButton styledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(9, 18, 9, 18));
        Color hover = bg.darker();
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hover); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(bg);    }
        });
        return btn;
    }

    /**
     * Applies the dark theme to a JTable including:
     *  - Coloured header row
     *  - Alternating row colours
     *  - Highlighted selection
     *  - Cell padding via custom renderer
     */
    private JTable styledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(FONT_NORMAL);
        table.setForeground(TEXT_LIGHT);
        table.setBackground(BG_CARD);
        table.setGridColor(BG_PANEL);
        table.setRowHeight(30);
        table.setSelectionBackground(PRIMARY);
        table.setSelectionForeground(Color.WHITE);
        table.setShowVerticalLines(false);
        table.setAutoCreateRowSorter(true);       // click header to sort

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_SUBTITLE);
        header.setBackground(TABLE_HEADER);
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder());

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) {
                    setBackground(row % 2 == 0 ? BG_CARD : TABLE_ROW_ALT);
                    setForeground(TEXT_LIGHT);
                }
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return this;
            }
        });
        return table;
    }

    /** Styles a JScrollPane to match the dark theme. */
    private void styleScrollPane(JScrollPane sp) {
        sp.setBorder(BorderFactory.createLineBorder(BG_PANEL));
        sp.getViewport().setBackground(BG_CARD);
        sp.getVerticalScrollBar().setBackground(BG_PANEL);
        sp.getHorizontalScrollBar().setBackground(BG_PANEL);
    }

    // ── Dialog shortcuts ───────────────────────────────────────────────────────

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInfo(String title, String msg) {
        JTextArea area = new JTextArea(msg);
        area.setEditable(false);
        area.setFont(FONT_NORMAL);
        area.setBackground(BG_CARD);
        area.setForeground(TEXT_LIGHT);
        area.setBorder(new EmptyBorder(10, 12, 10, 12));
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new Dimension(520, 280));
        styleScrollPane(sp);
        JOptionPane.showMessageDialog(this, sp, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /** Uppercases the first character of a string. */
    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
