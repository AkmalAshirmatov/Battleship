package akmal

class Cell(var x : Int, var y : Int) {
    var status : String = CELL_LIVE

    fun validCell() : Boolean {
        return x in XMIN..XMAX && y in YMIN..YMAX
    }

    fun changeState(cell : Cell) {
        if (cell.x == x && cell.y == y) {
            status = CELL_DEAD;
        }
    }
}