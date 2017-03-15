package messagerie.serveur.filtre;

public interface IFiltre {
	public boolean compare(Object objet);

	@Override
	public boolean equals(Object objet);

	@Override
	public int hashCode();
}
