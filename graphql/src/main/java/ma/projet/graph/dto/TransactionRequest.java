package ma.projet.graph.dto;

import lombok.Data;
import ma.projet.graph.entities.TypeTransaction;

import java.util.Date;

@Data
public class TransactionRequest {
    private Long compteId;           // L'ID du compte associé à la transaction
    private double montant;          // Montant de la transaction
    private Date date;             // Date de la transaction (au format String)
    private TypeTransaction type;    // Type de transaction : DEPOT ou RETRAIT
}