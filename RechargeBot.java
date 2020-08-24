public class RechargeBot {

    private double teleopTime = 135; // 2:15 = 135 s
    private double fieldLength = 52; // feet
    private double ballsAtATime = 5;

    private double speed = 10; // feet per second

    private double competitionDebuff = 1.2;
    private double rendezvousDebuff = 1.5;

    private double shootingBuff = 0.5;


    private boolean highGoalScoring;
    private double highGoalAccuracy = 0.6;
    private double innerGoalChance = 0.2;
    

    private boolean lowGoalScoring;
    private double lowGoalAccuracy = 1;

    private boolean defense;

    private boolean canSupport = lowGoalScoring;

    private boolean controlPanel;
    private boolean hanging;
    private boolean tall;

    private double cycleTime = (defense) ? 135 : cycleTime();

    
    public RechargeBot(boolean canShoot, boolean canDump, boolean canSpin, boolean canHang, boolean tall, boolean defense){
        this.highGoalScoring = canShoot;
        this.lowGoalScoring = canDump;
        this.controlPanel = canSpin;
        this.hanging = canHang;
        this.tall = tall;
        if (!(highGoalScoring && lowGoalScoring)) {
            this.defense = defense;
        }
    }

    public double cycleTime() {
        if (!(highGoalScoring && lowGoalScoring)){
            return teleopTime;
        }

        double initCycleTime = this.fieldLength / this.speed;
        double cycleTime = initCycleTime * this.competitionDebuff;
        if (tall) {
            cycleTime = cycleTime * this.rendezvousDebuff;
        }
        if (highGoalScoring) {
            cycleTime = cycleTime * this.shootingBuff;
        }

        return cycleTime;
    }

    public double pointsPerCycle() {
        double points = 0;

        if (highGoalScoring) {
            double pointsPerBall = 3 * this.innerGoalChance + 2 * (1 - this.innerGoalChance);
            double highGoalPoints = this.ballsAtATime * pointsPerBall;
            points = points + highGoalPoints;
        }

        if (lowGoalScoring) {
            double pointsPerBall = 1 * this.lowGoalAccuracy;
            double lowGoalPoints = 5 * pointsPerBall;
            points = points + lowGoalPoints;
        }

        return points;
    }

    public int maxScoredBalls(){
        return (int) ((int) 5 * this.teleopTime / this.cycleTime);
    }

    public double maxBallsPoints(){
        int cycles = (int) (this.teleopTime / this.cycleTime);
        return cycles * pointsPerCycle();
    }

    public void setCycleTime(double cycleTime) {
        this.cycleTime = cycleTime;
    }

    public double getCycleTime() {
        return cycleTime;
    }

    public boolean canShoot() {
        return this.highGoalScoring;
    }

    public boolean canDump() {
        return this.lowGoalScoring;
    }

    public boolean canSpin() {
        return this.controlPanel;
    }

    public boolean canHang() {
        return this.hanging;
    }

    public boolean canSupport() {
        return this.canSupport;
    }

    public double getHighGoalAccuracy() {
        return this.highGoalAccuracy;
    }

    public double getInnerGoalChance() {
        return innerGoalChance;
    }

    public double getLowGoalAccuracy() {
        return lowGoalAccuracy;
    }

    public double getFieldLength() {
        return fieldLength;
    }

    public double getTeleopTime() {
        return teleopTime;
    }

    public boolean getDefense() {
        return defense;
    }


    
}