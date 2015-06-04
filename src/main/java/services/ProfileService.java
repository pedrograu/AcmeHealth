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

    public Profile create(Specialist specialist2) {

        Profile profile = new Profile();
        Collection<Comment> comments = new HashSet<Comment>();

        profile.setComments(comments);
        profile.setSpecialist(specialist2);

        return profile;
    }

    public Profile save(Profile profile) {

        Administrator administratorConnect = administratorService.findByPrincipal();
        Assert.notNull(profile);
        Assert.isTrue(Administrator.class == administratorConnect.getClass());
        Profile profile1 = profileRepository.save(profile);

        return profile1;

    }

    public Profile save2(Profile profile) {
        Assert.notNull(profile);
        checkPrincipal(profile);

        Profile profile1 = profileRepository.save(profile);

        return profile1;

    }

    public Profile findOneToEdit(int profileId) {

        Profile profile = profileRepository.findOne(profileId);
        return profile;
    }

    public void checkPrincipal(Profile profile) {
        Specialist specialistConnect = specialistService.findByPrincipal();
        Assert.isTrue(profile.getSpecialist().equals(specialistConnect));

    }

    public void updateRating(Profile profile) {
        Double d = profileRepository.updateRating(profile.getId());
        profile.setRating(Math.round((d) * Math.pow(10, 2)) / Math.pow(10, 2));

        profileRepository.save(profile);

    }

    public void delete(Profile profile) {
        profileRepository.delete(profile);

    }

    public Collection<Profile> getBestSpecialist() {
        Collection<Profile> profiles;
        profiles = profileRepository.getBestSpecialist();
        return profiles;
    }

}
