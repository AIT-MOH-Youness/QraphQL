package ma.projet.graph.controllers;

import lombok.AllArgsConstructor;
import ma.projet.graph.dto.TransactionRequest;
import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TypeTransaction;
import ma.projet.graph.repositories.CompteRepository;
import ma.projet.graph.repositories.TransactionRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class TransactionControllerGraphQL {

    private final CompteRepository compteRepository;
    private final TransactionRepository transactionRepository;

    // Ajouter une nouvelle transaction
    @MutationMapping
    public Transaction addTransaction(@Argument TransactionRequest transactionRequest) {

        Compte compte = compteRepository.findById(transactionRequest.getCompteId())
                .orElseThrow(() -> new RuntimeException("Compte not found"));

        Transaction transaction = new Transaction();
        transaction.setMontant(transactionRequest.getMontant());
        transaction.setType(transactionRequest.getType());
        transaction.setDateTransaction(transactionRequest.getDateTransaction());
        transaction.setCompte(compte);

        return transactionRepository.save(transaction);
    }

    @QueryMapping
    public List<Transaction> compteTransactions(@Argument Long id) {

        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte not found"));


        return transactionRepository.findByCompte(compte);
    }

    @QueryMapping
    public List<Transaction> allTransactions() {
        return transactionRepository.findAll();
    }

    // Calculer tout pour les transactions
    @QueryMapping
    public Map<String, Object> transactionStats() {
        long count = transactionRepository.count(); // Nombre total de transactions
        Double sumDepots = transactionRepository.sumByType(TypeTransaction.DEPOT); // Somme des dépôts
        Double sumRetraits = transactionRepository.sumByType(TypeTransaction.RETRAIT); // Somme des retraits

        return Map.of(
                "count", count,
                "sumDepots", sumDepots != null ? sumDepots : 0,
                "sumRetraits", sumRetraits != null ? sumRetraits : 0
        );
    }
}
