package ma.projet.graph.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.TypeTransaction;

import java.util.Date;

@Data
public class TransactionRequest {
    private long compteId;
    private double montant;

    private Date dateTransaction;

    private TypeTransaction type;

}
