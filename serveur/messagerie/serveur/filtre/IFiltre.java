package messagerie.serveur.filtre;

import java.io.Serializable;

public interface IFiltre extends Serializable {
	public boolean compare(Object objet);

	@Override
	public boolean equals(Object objet);

	@Override
	public int hashCode();
}
