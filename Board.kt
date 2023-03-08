package akmal

class Board() {
    var cells = mutableListOf<Cell>()
    var ships = mutableListOf<Ship>()

    fun initFiredBoard() { // for board under attack
        for (x in XMIN..XMAX)
            for (y in YMIN..YMAX)
                cells.add(Cell(x, y))
    }

    fun checkCell(targetcell: Cell): String { // for board under attack, detecting wrong fires
        for (cell in cells) {
            if (targetcell.x == cell.x && targetcell.y == cell.y) {
                return cell.status;
            }
        }
        return CELL_DID_NOT_FIND
    }

    fun updateCellState(targetcell : Cell, flag : Int) { // if flag == 0, we missed shot, flag = 1, it is ship cell
        if (flag == 0) {
            for (cell in cells)
                cell.changeState(targetcell) // if cell == targetcell, do it dead
        }
        else {
            for (dx in -1..1) {
                for (dy in -1..1) {
                    for (cell in cells) {
                        cell.changeState(Cell(targetcell.x + dx, targetcell.y + dy))
                    }
                }
            }
        }

    }

    fun addShip(cell : Cell, size : Int, dir : String) : Boolean { // for hidden board
        var ship = Ship(cell, size, dir)
        if (!ship.validShip()) return false;
        var flag = true
        for (cellShip in ship.cells) {
            for (dx in -1..1) {
                for (dy in -1..1) {
                    var neighbourcell = Cell(cellShip.x + dx, cellShip.y + dy);
                    for (shipExist in ships) { // fix ship in already existing
                        for (cellExistShip in shipExist.cells) {
                            if (neighbourcell.x == cellExistShip.x && neighbourcell.y == cellExistShip.y)
                                flag = false
                        }
                    }
                }
            }
        }
        if (flag) {
            ships.add(ship); return flag;
        }
        else {
            return flag;
        }
    }

    fun fire(cell : Cell) : String { // for hidden board. If we attack cell, it is hidden
        for (ship in ships) {
            var res = ship.fire(cell)
            if (res == FIRED_SHIP_DONE) {
                return ship.status
            }
        } // SHIP_DONE, SHIP_FIRED, FIRED_MISSING
        return FIRED_MISSING
    }

    fun allShipsDead() : Boolean {
        var flag = true
        for (ship in ships)
            if (ship.status != SHIP_DEAD)
                flag = false
        return flag
    }
}