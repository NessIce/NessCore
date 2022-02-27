package br.com.ness.core.bukkit.profile.scoreboard;

import br.com.ness.core.bukkit.CorePlugin;
import br.com.ness.core.bukkit.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreCore {

    private Profile profile;

    private BukkitRunnable taskID;

    private Scoreboard scoreboard;
    private Objective health;

    private Objective sidebar;

    public ScoreCore(Profile profile){
        this.profile = profile;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        this.sidebar = scoreboard.registerNewObjective("fryScoreboard", "dummy");
        this.sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void activeHealth(){
        this.health = scoreboard.registerNewObjective("fryHealth", "dummy");
        this.health.setDisplaySlot(DisplaySlot.BELOW_NAME);
        this.health.setDisplayName("§c❤");
    }

    public Team getTeam(String name){
        return scoreboard.getTeam(name)!=null?scoreboard.getTeam(name):scoreboard.registerNewTeam(name);
    }

    public void setSidebar(Sidebar base){
        base.getScores(profile).forEach(scores->{
            sidebar.getScore(scores.getText()).setScore(scores.getLine());
            if(!scores.isEmpty()){
                Team team = getTeam(scores.getName());
                team.setSuffix(scores.getSuffix());
                team.addEntry(scores.getText());
            }
        });

        base.getRoles(profile).forEach(roles->{
            getTeam(roles.getName()).setPrefix(roles.getPrefix());
            getTeam(roles.getName()).setSuffix(roles.getSuffix());
        });

        taskID = new BukkitRunnable(){
            int i = 0;
            @Override
            public void run() {
                sidebar.setDisplayName(base.getDisplayName().get(i));

                if(i < base.getDisplayName().size()-1){
                    i++;
                }else{
                    i=0;
                }

                base.getScores(profile).forEach(scores-> {
                    if(!scores.isEmpty()){
                        getTeam(scores.getName()).setSuffix(scores.getSuffix());
                    }
                });

                base.getRoles(profile).forEach(roles->{
                    Team teamRole = getTeam(roles.getName());
                    base.applyRoles(profile, teamRole);
                });
            }
        };
        taskID.runTaskTimerAsynchronously(CorePlugin.getCore(),0,3);
    }

    public void destroy(){
        this.profile = null;
        if(taskID!=null){
            this.taskID.cancel();
        }
        this.scoreboard = null;
        this.health = null;
        this.sidebar = null;
    }

    public void apply(){
        profile.getPlayer().setScoreboard(scoreboard);
    }
}

