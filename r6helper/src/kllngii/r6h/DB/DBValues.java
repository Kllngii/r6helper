package kllngii.r6h.DB;

import java.util.List;

public class DBValues {
	
	String name;
	String id;
	int level;
	List<Rank> seasons;
	List<Stats> stats;
	GeneralStats generalStats;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public List<Rank> getSeasons() {
		return seasons;
	}
	public void setSeasons(List<Rank> seasons) {
		this.seasons = seasons;
	}
	public List<Stats> getStats() {
		return stats;
	}
	public void setStats(List<Stats> stats) {
		this.stats = stats;
	}
	public GeneralStats getGeneralStats() {
		return generalStats;
	}
	public void setGeneralStats(GeneralStats generalStats) {
		this.generalStats = generalStats;
	}
	
}
