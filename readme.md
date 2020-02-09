#HermeticMod

Adds the Hermetic character to Slay the Spire.

### Dev setup:
This is a bit complicated but only needs to be done once:
* Clone this directory to any directory on your local machine, we will call it `$PROJECT_DIR$`
* Subscribe to `ModTheSpire` and `BaseMod` on Steam workshop
* find the jars that steam downloads for these mods e.g. `C:/steam/steamapps/workshop/content/646570`
* copy these 2 jars to `$PROJECT_DIR$/../lib/`
* also copy these 2 jars to `C:/steam/steamapps/common/SlayTheSpire/mods`
* copy `desktop-1.0.jar` from `C:/steam/steamapps/common/SlayTheSpire/` into `$PROJECT_DIR$/../lib/`
* update `pom.xml` to point to your steam directory 
* `mvn clean install` this project, which should automatically install `HereticMod.jar` into `${steamdir}/SlayTheSpire/`
* run `C:/steam/steamapps/common/SlayTheSpire/MTS.cmd` to boot up Slay the Spire with mods enabled

## Play with mods
Check the instructions here: https://steamcommunity.com/games/646570/announcements/detail/1714081669582224415