package counterfeiters.views;

import javafx.scene.image.ImageView;

public class ListRow {
        private String gameId;
        private String lobbyName;
        private String numPlayers;
        private ImageView playerIcon;

        public ListRow(String gameId,String lobbyName, ImageView playerIcon) {
            this.lobbyName = lobbyName;

            this.gameId = gameId;
            this.playerIcon = playerIcon;
            //this.numPlayers = numPlayers;

            //resize pic
            this.playerIcon.setFitHeight(30);
            this.playerIcon.setFitWidth(30);

        }

        public String getLobbyName() {
            return lobbyName;
        }


        public String getNumPlayers() {
            return numPlayers;
        }

        public String getId(){
            return gameId;
        }
        public ImageView getIcon() {
            return playerIcon;
        }
    }

