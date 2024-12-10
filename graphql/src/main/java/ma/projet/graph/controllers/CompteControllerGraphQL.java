package ma.projet.graph.controllers;

import lombok.AllArgsConstructor;
import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TypeCompte;
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
public class CompteControllerGraphQL {

    private final CompteRepository compteRepository;
    private final TransactionRepository transactionRepository;

    // Récupérer tous les comptes
    @QueryMapping
    public List<Compte> allComptes(){
        return compteRepository.findAll();
    }

    // Récupérer un compte par son ID
    @QueryMapping
    public Compte compteById(@Argument Long id){
        return compteRepository.findById(id).orElseThrow(() ->
                new RuntimeException(String.format("Compte %s not found", id))
        );
    }

    // Récupérer les comptes par type
    @QueryMapping
    public List<Compte> compteByType(@Argument TypeCompte type){
        return compteRepository.findByType(type);  // Méthode à définir dans le repository
    }


    // Sauvegarder un compte
    @MutationMapping
    public Compte saveCompte(@Argument Compte compte){
        return compteRepository.save(compte);
    }

    // Supprimer un compte
    @MutationMapping
    public Boolean deleteCompte(@Argument Long id){
        compteRepository.deleteById(id);
        return true;
    }
    // Récupérer les statistiques des soldes
    @QueryMapping
    public Map<String, Object> totalSolde() {
        long count = compteRepository.count();
        double sum = compteRepository.sumSoldes();
        double average = count > 0 ? sum / count : 0;

        return Map.of(
                "count", count,
                "sum", sum,
                "average", average
        );
    }
}