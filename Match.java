public class Match {

    RechargeBot blue1;
    RechargeBot blue2;
    RechargeBot blue3;
    RechargeBot red1;
    RechargeBot red2;
    RechargeBot red3;

    RechargeBot[] blueAlliance = new RechargeBot[] {blue1, blue2, blue3};
    RechargeBot[] redAlliance = new RechargeBot[] {red1, red2, red3};

    private double supportBuff = 0.8;
    private double defenseDebuff = 1.2;

    private double redpoints = 0;
    private double bluepoints = 0;

    private boolean blueWin = game();

    public Match(RechargeBot blue1, RechargeBot blue2, RechargeBot blue3, RechargeBot red1, RechargeBot red2, RechargeBot red3){
        this.blue1 = blue1;
        this.blue2 = blue2;
        this.blue3 = blue3;
        this.red1 = red1;
        this.red2 = red2;
        this.red3 = red3;
    }

    public double getAutonomousPoints(RechargeBot[] alliance){
        double autonPoints = 0;
        double initationLinePoints = 5 * alliance.length;
        autonPoints = autonPoints + initationLinePoints;

        for (RechargeBot bot : alliance) {
            if (bot.canShoot()) {
                double pointsPerBall = 3 * bot.getInnerGoalChance() + 2 * (1 - bot.getInnerGoalChance());
                double autoShootPoints = 5 * pointsPerBall * bot.getHighGoalAccuracy();
                autonPoints = autonPoints + autoShootPoints;
            }

            if(bot.canDump()) {
                double pointsPerBall = 1 * bot.getLowGoalAccuracy();
                double autoDumpPoints = 5 * pointsPerBall;
                autonPoints = autonPoints + autoDumpPoints;
            }
        }

        return autonPoints;

    }

    public void getTeleOpPoints() {
        double redPoints = 0;
        double bluePoints = 0;

        if (blue1.canSupport()) {
            for (RechargeBot blueBot : blueAlliance) {
                blueBot.setCycleTime(blueBot.getCycleTime() * this.supportBuff);
            }
        }
        if (blue2.canSupport()) {
            for (RechargeBot blueBot : blueAlliance) {
                blueBot.setCycleTime(blueBot.getCycleTime() * this.supportBuff);
            }
        }
        if (blue3.canSupport()) {
            for (RechargeBot blueBot : blueAlliance) {
                blueBot.setCycleTime(blueBot.getCycleTime() * this.supportBuff);
            }
        }

        if(red1.getDefense()) {
            for (RechargeBot blueBot : blueAlliance) {
                blueBot.setCycleTime(blueBot.getCycleTime() * this.defenseDebuff);
            }
        }

        if(red2.getDefense()) {
            for (RechargeBot blueBot : blueAlliance) {
                blueBot.setCycleTime(blueBot.getCycleTime() * this.defenseDebuff);
            }
        }

        if(red3.getDefense()) {
            for (RechargeBot blueBot : blueAlliance) {
                blueBot.setCycleTime(blueBot.getCycleTime() * this.defenseDebuff);
            }
        }




        if (red1.canSupport()) {
            for (RechargeBot redBot : redAlliance) {
                redBot.setCycleTime(redBot.getCycleTime() * this.supportBuff);
            }
        }
        if (red2.canSupport()) {
            for (RechargeBot redBot : redAlliance) {
                redBot.setCycleTime(redBot.getCycleTime() * this.supportBuff);
            }
        }
        if (red3.canSupport()) {
            for (RechargeBot redBot : redAlliance) {
                redBot.setCycleTime(redBot.getCycleTime() * this.supportBuff);
            }
        }

        if(blue1.getDefense()) {
            for (RechargeBot redBot : redAlliance) {
                redBot.setCycleTime(redBot.getCycleTime() * this.defenseDebuff);
            }
        }

        if(blue2.getDefense()) {
            for (RechargeBot redBot : redAlliance) {
                redBot.setCycleTime(redBot.getCycleTime() * this.defenseDebuff);
            }
        }

        if(blue3.getDefense()) {
            for (RechargeBot redBot : redAlliance) {
                redBot.setCycleTime(redBot.getCycleTime() * this.defenseDebuff);
            }
        }


        for (RechargeBot bluebot: blueAlliance) {
            bluePoints = bluePoints + bluebot.maxBallsPoints();
        }

        for (RechargeBot redbot: redAlliance) {
            redPoints = redPoints + redbot.maxBallsPoints();
        }

        bluePoints = bluePoints + getControlPanelPoints(blueAlliance);
        redPoints = redPoints + getControlPanelPoints(redAlliance);

        this.redpoints = this.redpoints + redPoints;
        this.bluepoints = this.bluepoints + bluePoints;
    }

    public double getControlPanelPoints(RechargeBot[] alliance){
        int possiblePowerCellsScored = 0;
        for (RechargeBot bot: alliance) {
            possiblePowerCellsScored = possiblePowerCellsScored + bot.maxScoredBalls();
        }

        if (possiblePowerCellsScored < 29) {
            return 0;
        } else if (possiblePowerCellsScored < 49) {
            return 10;
        } else {
            return 30;
        }


    }

    public double getEndGamePoints(RechargeBot[] alliance) {
        double endGamePoints = 0;
        for (RechargeBot bot : alliance) {
            if(bot.canHang()) {
                endGamePoints = endGamePoints + 25;
            } else {
                endGamePoints = endGamePoints + 5;
            }
        } 
        return endGamePoints;       
    }

    public boolean game(){
        this.bluepoints = this.bluepoints + getAutonomousPoints(blueAlliance);
        this.redpoints = this.redpoints + getAutonomousPoints(redAlliance);
        getTeleOpPoints();
        this.bluepoints = this.bluepoints + getEndGamePoints(blueAlliance);
        this.bluepoints = this.bluepoints + getEndGamePoints(redAlliance);

        return (bluepoints > redpoints) ? true : false;
    }
    
}