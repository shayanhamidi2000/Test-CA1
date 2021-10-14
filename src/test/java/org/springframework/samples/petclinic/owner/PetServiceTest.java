package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import java.util.*;

public class PetServiceTest {
    public static class fakeOwnerRepository implements OwnerRepository {
        @Override
        public Collection<Owner> findByLastName(String lastName) {
            return null;
        }

        @Override
        public Owner findById(Integer id) {
            return null;
        }

        @Override
        public void save(Owner owner) {

        }
    }
    public static class fakeLogger implements Logger {
        @Override public String getName() {return null;}
        @Override public boolean isTraceEnabled() {return false;}
        @Override public void trace(String s) {}
        @Override public void trace(String s, Object o) {}
        @Override public void trace(String s, Object o, Object o1) {}
        @Override public void trace(String s, Object... objects) {}
        @Override public void trace(String s, Throwable throwable) {}
        @Override public boolean isTraceEnabled(Marker marker) {return false;}
        @Override public void trace(Marker marker, String s) {}
        @Override public void trace(Marker marker, String s, Object o) {}
        @Override public void trace(Marker marker, String s, Object o, Object o1) {}
        @Override public void trace(Marker marker, String s, Object... objects) {}
        @Override public void trace(Marker marker, String s, Throwable throwable) {}
        @Override public boolean isDebugEnabled() {return false;}
        @Override public void debug(String s) {}
        @Override public void debug(String s, Object o) {}
        @Override public void debug(String s, Object o, Object o1) {}
        @Override public void debug(String s, Object... objects) {}
        @Override public void debug(String s, Throwable throwable) {}
        @Override public boolean isDebugEnabled(Marker marker) {return false;}
        @Override public void debug(Marker marker, String s) {}
        @Override public void debug(Marker marker, String s, Object o) {}
        @Override public void debug(Marker marker, String s, Object o, Object o1) {}
        @Override public void debug(Marker marker, String s, Object... objects) {}
        @Override public void debug(Marker marker, String s, Throwable throwable) {}
        @Override public boolean isInfoEnabled() {return false;}
        @Override public void info(String s) {}
        @Override public void info(String s, Object o) {}
        @Override public void info(String s, Object o, Object o1) {}
        @Override public void info(String s, Object... objects) {}
        @Override public void info(String s, Throwable throwable) {}
        @Override public boolean isInfoEnabled(Marker marker) {return false;}
        @Override public void info(Marker marker, String s) {}
        @Override public void info(Marker marker, String s, Object o) {}
        @Override public void info(Marker marker, String s, Object o, Object o1) {}
        @Override public void info(Marker marker, String s, Object... objects) {}
        @Override public void info(Marker marker, String s, Throwable throwable) {}
        @Override public boolean isWarnEnabled() {return false;}
        @Override public void warn(String s) {}
        @Override public void warn(String s, Object o) {}
        @Override public void warn(String s, Object... objects) {}
        @Override public void warn(String s, Object o, Object o1) {}
        @Override public void warn(String s, Throwable throwable) {}
        @Override public boolean isWarnEnabled(Marker marker) {return false;}
        @Override public void warn(Marker marker, String s) {}
        @Override public void warn(Marker marker, String s, Object o) {}
        @Override public void warn(Marker marker, String s, Object o, Object o1) {}
        @Override public void warn(Marker marker, String s, Object... objects) {}
        @Override public void warn(Marker marker, String s, Throwable throwable) {}
        @Override public boolean isErrorEnabled() {return false;}
        @Override public void error(String s) {}
        @Override public void error(String s, Object o) {}
        @Override public void error(String s, Object o, Object o1) {}
        @Override public void error(String s, Object... objects) {}
        @Override public void error(String s, Throwable throwable) {}
        @Override public boolean isErrorEnabled(Marker marker) {return false;}
        @Override public void error(Marker marker, String s) {}
        @Override public void error(Marker marker, String s, Object o) {}
        @Override public void error(Marker marker, String s, Object o, Object o1) {}
        @Override public void error(Marker marker, String s, Object... objects) {}
        @Override public void error(Marker marker, String s, Throwable throwable) {}
    }
    public static class fakePetRepository implements PetRepository {
        private final Map<Integer, Pet> fakeMap = new HashMap<>();

        @Override
        public List<PetType> findPetTypes() {
            return null;
        }

        @Override
        public Pet findById(Integer id) {
            return fakeMap.get(id);
        }

        @Override
        public void save(Pet pet) {
            fakeMap.put(pet.getId(), pet);
        }
    }

    private static PetService cut;

    private static Collection<Object[]> findPet_providedDemandedPetId_correspondingPetMustBeReturned(){
        cut = new PetService(new PetTimedCache(new fakePetRepository()), new fakeOwnerRepository(), new fakeLogger());
        Owner dummyOwner = new Owner();
        Pet firstDemandedPet = new Pet();
        firstDemandedPet.setId(1);
        Pet secondedDemandedPet = new Pet();
        secondedDemandedPet.setId(2);
        Pet thirdDemandedPet = new Pet();
        thirdDemandedPet.setId(3);
        cut.savePet(firstDemandedPet, dummyOwner);
        cut.savePet(secondedDemandedPet, dummyOwner);
        cut.savePet(thirdDemandedPet, dummyOwner);
        return Arrays.asList(new Object[][] {{1, firstDemandedPet},
                {2, secondedDemandedPet},
                {3, thirdDemandedPet}}
        );
    }

    @ParameterizedTest
    @MethodSource
    public void findPet_providedDemandedPetId_correspondingPetMustBeReturned(Integer demandedPetId, Pet expectedDemandedPet) {
        // Act
        Pet actualDemandedPet = cut.findPet(demandedPetId);

        // Assert
        Assertions.assertEquals(expectedDemandedPet, actualDemandedPet, "Pet and the id are not related!");
    }
}
