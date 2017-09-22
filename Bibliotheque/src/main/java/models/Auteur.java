package models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Auteur")
public class Auteur {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idAuteur;
	
	@Column(name="nom", nullable=false)
	private String nom;
	
	@Column(name="prenom")
	private String prenom;
	
	@Enumerated(EnumType.STRING)
	private Langue langue;
	
//	@JsonIgnore
	@OneToMany(mappedBy="auteur", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<Livre> livre;
	
	public Auteur() {
		
	}

	public Auteur(
			String nom, 
			String prenom, 
			Langue langue
	) {
		this.nom = nom;
		this.prenom = prenom;
		this.langue = langue;
	}

	
	/*
	 * GETTERS & SETTERS
	 */
	
	/**
	 * @return the idAuteur
	 */
	public int getIdAuteur() {
		return idAuteur;
	}

	/**
	 * @param idAuteur the idAuteur to set
	 */
	public void setIdAuteur(int idAuteur) {
		this.idAuteur = idAuteur;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the langue
	 */
	public Langue getLangue() {
		return langue;
	}

	/**
	 * @param langue the langue to set
	 */
	public void setLangue(Langue langue) {
		this.langue = langue;
	}

	/**
	 * @return the livre
	 */
	public Set<Livre> getLivre() {
		return livre;
	}

	/**
	 * @param livre the livre to set
	 */
	public void setLivre(Set<Livre> livre) {
		this.livre = livre;
	}
	
	
	
}
