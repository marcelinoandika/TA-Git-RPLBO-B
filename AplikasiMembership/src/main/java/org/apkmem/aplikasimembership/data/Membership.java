package org.apkmem.aplikasimembership.data;
/**
 * @editor David.Seay-71220909
 */
public class Membership {
    private int id ;
    private String nama_apk;
    private String jenis_membership;
    private String tgl_mulai;
    private String tgl_berhenti;
    private int id_user;
    private String status;
    private String deskripsi;

//    CB
    public static String JENIS_PAKET_BRONZE = "BRONZE";
    public static String JENIS_PAKET_SILVER = "SILVER";
    public static String JENIS_PAKET_GOLD = "GOLD";

    public Membership(int id, String nama_apk, String jenis_membership, String tgl_mulai, String tgl_berhenti, String status, String deskripsi) {
        this.setId(id);
        this.setNama_apk(nama_apk);
        this.setJenis_membership(jenis_membership);
        this.setTgl_mulai(tgl_mulai);
        this.setTgl_berhenti(tgl_berhenti);
        this.setStatus(status);
        this.setDeskripsi(deskripsi);
    }

    public Membership(String nama_apk, String jenis_membership, String tgl_mulai, String tgl_berhenti, String status, int id_user, String deskripsi) {
        this.setNama_apk(nama_apk);
        this.setJenis_membership(jenis_membership);
        this.setTgl_mulai(tgl_mulai);
        this.setTgl_berhenti(tgl_berhenti);
        this.setStatus(status);
        this.setId_user(id_user);
        this.setDeskripsi(deskripsi);
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_apk() {
        return nama_apk;
    }

    public void setNama_apk(String nama_apk) {
        this.nama_apk = nama_apk;
    }

    public String getJenis_membership() {
        return jenis_membership;
    }

    public void setJenis_membership(String jenis_membership) {
        this.jenis_membership = jenis_membership;
    }

    public String getTgl_mulai() {
        return tgl_mulai;
    }

    public void setTgl_mulai(String tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }

    public String getTgl_berhenti() {
        return tgl_berhenti;
    }

    public void setTgl_berhenti(String tgl_berhenti) {
        this.tgl_berhenti = tgl_berhenti;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
