package org.springframework.samples.petclinic.owner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class OwnerTest {
    private Owner cut;

    @Before
    public void setup() {
        cut = new Owner();
    }

    @After
    public void tearDown() {
        cut = null;
    }

    @Test
    public void getOrSetAddress_providedAddressWasSet_theProvidedAddressMustBeReturned() {
        // Arrange
        String providedAddress = new String("Address");

        // Act
        cut.setAddress(providedAddress);
        String actualAddress = cut.getAddress();

        // Assert
        assertEquals("Addresses do not match!", providedAddress, actualAddress);
    }

    @Test
    public void getPetsInternal_providedNotNullPetSet_theProvidedPetSetReturned() {
        // Arrange
        Set<Pet> providedPetSet = new HashSet<>();
        providedPetSet.add(new Pet());

        // Act
        cut.setPetsInternal(providedPetSet);
        Set<Pet> actualPetSet = cut.getPetsInternal();

        // Assert
        assertEquals("Sets do not match!", providedPetSet, actualPetSet);
    }

    @Test
    public void getPetsInternal_providedNullPetSet_anEmptyHashSetReturned() {
        // Arrange
        Set<Pet> providedPetSet = null;
        Set<Pet> expectedPetSet = new HashSet<>();

        // Act
        cut.setPetsInternal(providedPetSet);
        Set<Pet> actualPetSet = cut.getPetsInternal();

        // Assert
        assertEquals("Sets do not match!", expectedPetSet, actualPetSet);
    }

    @Test
    public void addPet_providedANewPet_newPetIsAddedToThePetSet() {
        // Arrange
        Pet providedPet = new Pet();
        Set<Pet> petSet = new HashSet<>();
        cut.setPetsInternal(petSet);

        // Act
        cut.addPet(providedPet);

        // Assert
        assertTrue("providedPet Is not in the PetSet!", (cut.getPetsInternal().size() == 1) && (cut.getPetsInternal().contains(providedPet)));
        assertEquals("petOwners do not match!", cut, providedPet.getOwner());
    }
}
