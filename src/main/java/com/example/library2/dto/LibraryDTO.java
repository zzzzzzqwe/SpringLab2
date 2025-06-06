package com.example.library2.dto;

public class LibraryDTO {
    private Long id;
    private String location;

    public LibraryDTO() {}

    public LibraryDTO(Long id, String location) {
        this.id = id;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
