public class UserInventoryTests {
  public void testSkillManipulation() {
    Inventory inv = new Inventory();
    Skill skill = new Skill("Skill Name", "category");
    
    // Testing adding a skill to inventory
    inv.add(skill);
    assertTrue(inv.get(0).equals(skill));
    
    // Testing modifying a skill in inventory
    Photo dog = new Photo("dog chasing it's tail");
    Skill held_skill = inv.get(0);
    held_skill.setDescription("I jumped and then got really tired");
    held_skill.setPhoto(dog);
    held_skill.setVisibility(false);
    assertTrue(dog.equals(held_skill.getPhoto()));
    assertTrue(held_skill.getDescription().equals("I jumped and then got really tired"));
    assertTrue(held_skill.getVisibility().equals(false));
    
    // Testing removing a skill from inventory
    inv.remove(skill);
    assertTrue(inv.size()==0);
    
    
  }
}
