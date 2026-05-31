package com.example.learnwave.model.entity;

import com.example.learnwave.enums.StatusConteudo;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "videoaulas")
public class Videoaula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String titulo;
    private String descricao;
    @Column(nullable = false)
    private String area;
    @Column(name = "professor_id", nullable = false)
    private Integer professorId;
    @Column(name = "url_video")
    private String urlVideo;
    private String duracao;
    @Enumerated(EnumType.STRING)
    private StatusConteudo status;
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Relacionamentos
    @Transient
    private Usuario professor;
    @Transient
    private List<ProgressoVideoaula> progressos;

    public Videoaula() {}

    public Videoaula(String titulo, String descricao, String area, Integer professorId, String urlVideo, String duracao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.area = area;
        this.professorId = professorId;
        this.urlVideo = urlVideo;
        this.duracao = duracao;
        this.status = StatusConteudo.RASCUNHO;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Extrai o thumbnail do YouTube a partir da URL do vídeo.
     * Suporta formatos: youtube.com/watch?v=ID, youtu.be/ID, youtube.com/embed/ID
     */
    public void gerarThumbnailDoYouTube() {
        if (this.urlVideo == null || this.urlVideo.trim().isEmpty()) {
            return;
        }
        String videoId = extrairVideoIdYouTube(this.urlVideo);
        if (videoId != null) {
            this.thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";
        }
    }

    private String extrairVideoIdYouTube(String url) {
        if (url == null) return null;
        // Formato: https://www.youtube.com/watch?v=VIDEO_ID
        if (url.contains("watch?v=")) {
            String[] parts = url.split("watch\\?v=");
            if (parts.length > 1) {
                String id = parts[1].split("[&?#]")[0];
                return id.isEmpty() ? null : id;
            }
        }
        // Formato: https://youtu.be/VIDEO_ID
        if (url.contains("youtu.be/")) {
            String[] parts = url.split("youtu\\.be/");
            if (parts.length > 1) {
                String id = parts[1].split("[?&#]")[0];
                return id.isEmpty() ? null : id;
            }
        }
        // Formato: https://www.youtube.com/embed/VIDEO_ID
        if (url.contains("/embed/")) {
            String[] parts = url.split("/embed/");
            if (parts.length > 1) {
                String id = parts[1].split("[?&#]")[0];
                return id.isEmpty() ? null : id;
            }
        }
        return null;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public Integer getProfessorId() { return professorId; }
    public void setProfessorId(Integer professorId) { this.professorId = professorId; }

    public String getUrlVideo() { return urlVideo; }
    public void setUrlVideo(String urlVideo) { this.urlVideo = urlVideo; }

    public String getDuracao() { return duracao; }
    public void setDuracao(String duracao) { this.duracao = duracao; }

    public StatusConteudo getStatus() { return status; }
    public void setStatus(StatusConteudo status) { this.status = status; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public Usuario getProfessor() { return professor; }
    public void setProfessor(Usuario professor) { this.professor = professor; }

    public List<ProgressoVideoaula> getProgressos() { return progressos; }
    public void setProgressos(List<ProgressoVideoaula> progressos) { this.progressos = progressos; }
}
