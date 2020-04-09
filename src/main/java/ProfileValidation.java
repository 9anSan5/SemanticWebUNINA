import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWL2ELProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;

public class ProfileValidation {

    public static void main(String[] args) throws OWLOntologyCreationException {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        IRI pizzaontology = IRI.create(
                "http://protege.stanford.edu/ontologies/pizza/pizza.owl");
        OWLOntology o = man.loadOntology(pizzaontology);

        // Available profiles: DL, EL, QL, RL, OWL2 (Full)
        OWL2ELProfile profile = new OWL2ELProfile();
        OWLProfileReport report = profile.checkOntology(o);
        for(OWLProfileViolation v:report.getViolations()) {
            System.out.println(v);
        }
    }
}
