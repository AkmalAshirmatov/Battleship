package akmal

class Ship {
    var size = 0
    var cells = mutableListOf<Cell>()
    var status = ""

    constructor(cell : Cell, size : Int, dir : String) {
        if (dir == SHIP_RIGHT) {
            this.size = size
            for (delta in 0 until size) //from 0 to size - 1
                cells.add(Cell(cell.x + delta, cell.y))
            status = SHIP_LIVE
        }
        else if (dir == SHIP_UP) {
            this.size = size
            for (delta in 0 until size) //from 0 to size - 1
                cells.add(Cell(cell.x, cell.y + delta))
            status = SHIP_LIVE
        }
    }

    fun validShip() : Boolean {
        var flag = true
        for (cell in cells) {
            if (!cell.validCell())
                flag = false;
        }
        return flag
    }

    private fun updateShip() { // for hidden board, is ship dead?
        var flag = true
        for (cell in cells) {
            if (cell.status != CELL_DEAD)
                flag = false
        }
        if (flag) status = SHIP_DEAD
    }

    fun fire(targetcell : Cell) : String {
        for (cell in cells) {
            if (cell.x == targetcell.x && cell.y == targetcell.y) {
                cell.status = CELL_DEAD
                status = SHIP_FIRED
                updateShip()
                return FIRED_SHIP_DONE
            }
        }
        return FIRED_SHIP_MISSED
    }
}