package akmal

fun main(args: Array<String>) {
    var hiddenBoard = Board()

    var boardUnterAttack = Board()
    boardUnterAttack.initFiredBoard()

    println("""
        Enter ships in the following order:
        4 times size 1, 3 times size 2, 2 times size 2, 1 times size 4
        Be careful! You should write <x> <y> <size> <direction> (can be U - up, R - right)""")

    for (sizeShip in 1..4) {
        var cnt = 5 - sizeShip
        for (times in 1..cnt) {
            println("sizeship = $sizeShip, number = $times")
            while (true) {
                var (x, y, size, direction) = readLine()!!.split(' ')
                if (size.toInt() != sizeShip) {
                    println("Wrong input, try again")
                    continue
                }
                val res = hiddenBoard.addShip(Cell(x.toInt(), y.toInt()), size.toInt(), direction)
                if (res) {
                    println("Added!")
                    break
                }
                else {
                    println("Wrong input, try again")
                }
            }
        }
    }

    while (true) {
        var (sx, sy) = readLine()!!.split(' ')
        var cell = Cell(sx.toInt(), sy.toInt())
        if (!cell.validCell()) {
            println("Play by the rules. You shoot past the field!")
            continue
        }

        var res = boardUnterAttack.checkCell(cell)
        if (res == CELL_DEAD) {
            println("What are you doing? Choose another cell!")
            continue
        }

        res = hiddenBoard.fire(cell)
        if (res == FIRED_MISSING) {
            println("You missed")
            boardUnterAttack.updateCellState(cell, 0)
        }
        else if (res == SHIP_FIRED) {
            println("Good shot! You shot a ship!")
            boardUnterAttack.updateCellState(cell, 0)
        }
        else if (res == SHIP_DEAD) {
            println("Good shot! This ship is dead!")
            for (ship in hiddenBoard.ships) {
                if (ship.status == SHIP_DEAD) {
                    for (shipcell in ship.cells)
                        boardUnterAttack.updateCellState(shipcell, 1)
                }
            }
        }

        if (hiddenBoard.allShipsDead()) {
            println("You win!")
            break
        }
    }
}

