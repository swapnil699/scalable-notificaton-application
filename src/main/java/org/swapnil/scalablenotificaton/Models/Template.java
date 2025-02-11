//package org.swapnil.scalablenotificaton.Models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity(name = "templates")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Template {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @Column(columnDefinition = "TEXT")
//    private String content;
//
//    @Column(columnDefinition = "JSON")
//    private String placeholders;
//
//    private int templatePriority;
//
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    private LocalDateTime updatedAt = LocalDateTime.now();
//}

package org.swapnil.scalablenotificaton.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "JSON")
    private String placeholders;

    private int templatePriority;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    public Template() {}

    public Template(Long id, String name, String content, String placeholders,
                    int templatePriority, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.placeholders = placeholders;
        this.templatePriority = templatePriority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPlaceholders() {
        return placeholders;
    }
    public void setPlaceholders(String placeholders) {
        this.placeholders = placeholders;
    }
    public int getTemplatePriority() {
        return templatePriority;
    }
    public void setTemplatePriority(int templatePriority) {
        this.templatePriority = templatePriority;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
