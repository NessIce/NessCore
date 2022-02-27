package br.com.ness.core.bukkit.profile.scoreboard;


import br.com.ness.core.bukkit.profile.Profile;
import org.bukkit.scoreboard.Team;

import java.util.List;

public abstract class Sidebar {

    public abstract List<String> getDisplayName();
    public abstract List<VirtualTeam> getScores(Profile profile);
    public abstract List<VirtualTeam> getRoles(Profile profile);
    public abstract void applyRoles(Profile profile, Team teamRole);

}
