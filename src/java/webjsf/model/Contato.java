/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webjsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author i03
 */
@Entity
public class Contato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column
    private String email;
    @Column
    private String endereco;
    @Temporal(TemporalType.DATE)
    private Calendar dataNascimento;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "ID_CONTATO")
    @JoinTable(name = "TELEFONES")
    private final List<Telefone> telefones = new ArrayList<Telefone>();

    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String novo) {
        this.nome = novo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String novo) {
        this.email = novo;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String novo) {
        this.endereco = novo;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long novo) {
        this.id = novo;
    }

    @Override
    public String toString() {
        return nome;
    }

    public void adicionaTelefone(Telefone telefone) {
        telefone.setContato(this);
        for (Iterator<Telefone> iterator = telefones.iterator(); iterator.hasNext();) {
            Telefone _telefone = iterator.next();

            if(_telefone.getNumero().equals(telefone.getNumero())){
                iterator.remove();
                telefones.add(telefone);
                return;
            }
        }
        
        telefones.add(telefone);
    }


    public String getTelefone() {
        if (telefones != null && !telefones.isEmpty()) {
            for (Telefone telefone : telefones) {
                if (telefone.getTipo() == Telefone.Tipo.CELULAR) {
                    return telefone.getNumero();
                }
            }

            return telefones.iterator().next().getNumero();
        }

        return null;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

}
