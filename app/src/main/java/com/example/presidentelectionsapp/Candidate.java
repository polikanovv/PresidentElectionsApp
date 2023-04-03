package com.example.presidentelectionsapp;

public class Candidate {
    private String id;
    private String firstname;
    private String secondname;
    private String party;
    private String descriptions;
    private String web;
    private String image;
    private String votes;
    private String totalVotes;
    private String gender;
    private boolean isChecked;

    Candidate(String id, String firstname, String secondname, String party, String descriptions, String web, String image, String votes, String totalVotes, String gender, boolean isChecked){
        this.id = id;
        this.firstname = firstname;
        this.secondname = secondname;
        this.party = party;
        this.descriptions = descriptions;
        this.web = web;
        this.image = image;
        this.votes = votes;
        this.totalVotes = totalVotes;
        this.gender = gender;
        this.isChecked = isChecked;
    }

    Candidate(){
        this.id = "";
        this.firstname = "";
        this.secondname = "";
        this.party = "";
        this.descriptions = "";
        this.web = "";
        this.image = "";
        this.votes = "";
        this.totalVotes = "";
        this.gender = "";
        this.isChecked = false;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }
    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getParty() {
        return party;
    }
    public void setParty(String party) {
        this.party = party;
    }

    public String getDescriptions() {
        return descriptions;
    }
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getWeb() {
        return web;
    }
    public void setWeb(String web) {
        this.web = web;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getVotes() {
        return votes;
    }
    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getTotalVotes() { return totalVotes; }
    public void setTotalVotes(String totalVotes) { this.totalVotes = totalVotes; }

    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}
