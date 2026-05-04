package org.example.domain.models;

import lombok.Getter;


@Getter
public class Client {
  private Long id;
  private  String nom;
  private String prenom;
  private String email;
  private boolean esFidel;


  public boolean estActif() {
    return true;
  }

}
