package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfileRepository;
import domain.Administrator;
import domain.Comment;
import domain.Profile;
import domain.Specialist;

@Service
@Transactional
public class ProfileService {

    // Managed repository ---------------------------------------

    @Autowired
    private ProfileRepository profileRepository;

    // Managed service ---------------------------------------
    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private AdministratorService administratorService;

    // Constructors ---------------------------------------------

    public ProfileService() {
        super();
    }

    // Simple CRUD methods --------------------------------------

    //Crea un profile para un especialista
    public Profile create(Specialist specialist2) {

        Profile profile = new Profile();
        Collection<Comment> comments = new HashSet<Comment>();

        profile.setComments(comments);
        profile.setSpecialist(specialist2);

        return profile;
    }

    //Guarda un profile en la base de datos
    public Profile save(Profile profile) {

        Administrator administratorConnect = administratorService.findByPrincipal();
        Assert.notNull(profile);
        
        //Comprueba que el usuario logueado en el sistema es un administrador
        Assert.isTrue(Administrator.class == administratorConnect.getClass());
        Profile profile1 = profileRepository.save(profile);

        return profile1;

    }

  //Guarda un profile en la base de datos
    public Profile save2(Profile profile) {
        Assert.notNull(profile);
        checkPrincipal(profile);

        Profile profile1 = profileRepository.save(profile);

        return profile1;

    }

    //Devuelve un profile pasado su id
    public Profile findOneToEdit(int profileId) {

        Profile profile = profileRepository.findOne(profileId);
        return profile;
    }

    //Comprueba que el perfil es del especialista logueado en el sistema
    public void checkPrincipal(Profile profile) {
        Specialist specialistConnect = specialistService.findByPrincipal();
        Assert.isTrue(profile.getSpecialist().equals(specialistConnect));

    }

    //Actualiza la valoracion del perfil de un especialista
    public void updateRating(Profile profile) {
        Double d = profileRepository.updateRating(profile.getId());
        profile.setRating(Math.round((d) * Math.pow(10, 2)) / Math.pow(10, 2));

        profileRepository.save(profile);

    }

    //Elimina de la base de datos un profile
    public void delete(Profile profile) {
        profileRepository.delete(profile);

    }

    //Devuelve los especialistas que tienen la mejor valoracion
    public Collection<Profile> getBestSpecialist() {
        Collection<Profile> profiles;
        profiles = profileRepository.getBestSpecialist();
        return profiles;
    }

}
