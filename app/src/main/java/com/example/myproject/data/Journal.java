package com.example.myproject.data;

public class Journal {
    private String add_note;
    private String add_thought;

    public Journal() { }

    public Journal(String add_note, String add_thought) {
        this.add_note = add_note;
        this.add_thought = add_thought;
    }

    public String get_note() {
        return add_note;
    }

    public void set_note(String add_note) {
        this.add_note = add_note;
    }

    public String get_thought() {
        return add_thought;
    }

    public void set_thought(String add_thought) {
        this.add_thought = add_thought;
    }
}
