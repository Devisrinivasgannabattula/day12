package com.taskforge.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "TF_PROJECTS")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 120)
    private String name;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;
    
    @OneToMany(
    	    mappedBy = "project",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    	)
    	private List<Task> tasks;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private User owner;

    public Project() {}
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}
