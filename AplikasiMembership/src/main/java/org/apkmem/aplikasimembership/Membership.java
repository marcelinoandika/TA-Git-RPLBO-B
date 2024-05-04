package org.apkmem.aplikasimembership;

public class Membership {
    private int id ;
    private String nama_apk;
    private String jenis_membership;
    private String tgl_mulai;
    private String tgl_berhenti;
    private String status;

    public Membership(int id, String nama_apk, String jenis_membership, String tgl_mulai, String tgl_berhenti, String status) {
        this.setId(id);
        this.setNama_apk(nama_apk);
        this.setJenis_membership(jenis_membership);
        this.setTgl_mulai(tgl_mulai);
        this.setTgl_berhenti(tgl_berhenti);
        this.setStatus(status);
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

}
