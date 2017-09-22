package models;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Livre")
public class Livre {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idLivre;
	
	@Column(name="titre", nullable=false)
	private String titre;
	
	@Column(name="datePublication", nullable=false)
	private Date datePublication;
	
	@Column(name="description")
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Categorie categorie;
	
	@Column(name="nbExemplaires")
	private int nbExemplaire;
	
	@Column(name="nbDisponible")
	private int nbDisponible;
	
	@ManyToOne
	private models.Auteur auteur;
	
	public Livre() {
		
	}

	public Livre(
			String titre, 
			Date datePublication, 
			String description, 
			Categorie categorie, 
			int nbExemplaire,
			int nbDisponible,
			models.Auteur auteur
	) {
		this.titre = titre;
		this.datePublication = datePublication;
		this.description = description;
		this.categorie = categorie;
		this.nbExemplaire = nbExemplaire;
		this.nbDisponible = nbDisponible;
		this.auteur = auteur;
	}

	
	/*
	 * GETTERS & SETTERS
	 */
	
	
	/**
	 * @return the idLivre
	 */
	public int getIdLivre() {
		return idLivre;
	}

	/**
	 * @param idLivre the idLivre to set
	 */
	public void setIdLivre(int idLivre) {
		this.idLivre = idLivre;
	}

	/**
	 * @return the titre
	 */
	public String getTitre() {
		return titre;
	}

	/**
	 * @param titre the titre to set
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}

	/**
	 * @return the datePublication
	 */
	public Date getDatePublication() {
		return datePublication;
	}

	/**
	 * @param datePublication the datePublication to set
	 */
	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the categorie
	 */
	public Categorie getCategorie() {
		return categorie;
	}

	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}


	/**
	 * @return the nbExemplaire
	 */
	public int getNbExemplaire() {
		return nbExemplaire;
	}

	/**
	 * @param nbExemplaire the nbExemplaire to set
	 */
	public void setNbExemplaire(int nbExemplaire) {
		this.nbExemplaire = nbExemplaire;
	}

	/**
	 * @return the nbDisponible
	 */
	public int getNbDisponible() {
		return nbDisponible;
	}

	/**
	 * @param nbDisponible the nbDisponible to set
	 */
	public void setNbDisponible(int nbDisponible) {
		this.nbDisponible = nbDisponible;
	}

	/**
	 * @return the auteur
	 */
	public models.Auteur getAuteur() {
		return auteur;
	}

	/**
	 * @param auteur the auteur to set
	 */
	public void setAuteur(models.Auteur auteur) {
		this.auteur = auteur;
	}
	
	
	
	
	
	
}
