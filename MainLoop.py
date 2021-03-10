# 11 for the selected piece
# 12 for where we can move the selected piece
# 0 for legal emtpy space
# 9 illegal space
# 1-6 players

import pygame as pg
from Board import Board
from AI import AI

pg.init()
clock = pg.time.Clock()
running = True
window = pg.display.set_mode((640*2, 480*2))
window.fill((255, 255, 255))
rectHeight = 50
rectWidth = 50

players = 2
b = Board(players)
ai = AI(b)

COLOR0=(0,0,255)
COLOR1=(0,255,255)
COLOR2=(255,255,255)
COLOR9=(0,0,0)
COLOR11=(255,0,0)
COLOR12 = (255,255,0)

def validMoveWalk(b , x, y):
    try:
        if b.getEntry(x+1,y+1) == 0:
            b.setEntry(x+1,y+1, 12)
    except:
        pass

    try:
        if b.getEntry(x-1,y-1) == 0:
            b.setEntry(x-1,y-1, 12)

    except:
        pass
    try:
        if b.getEntry(x+1,y-1) == 0:
            b.setEntry(x+1,y-1, 12)

    except:
        pass
    try:

        if b.getEntry(x-1,y+1) == 0:
            b.setEntry(x-1,y+1, 12)

    except:
        pass


def validMoveJump(b, x, y):
    # some of these calls crush, we might need a try except
    try:

        if b.getEntry(x+1, y+1) in range(1, 7):
            if (b.getEntry(x+2, y+2) == 0):
                b.setEntry(x+2, y+2, 12)
                validMoveJump(b, x+2, y+2)
    except:
        pass

    try:
        if b.getEntry(x-1, y-1) in range(1, 7):
            if (b.getEntry(x-2, y-2) == 0):
                b.setEntry(x-2, y-2, 12)
                validMoveJump(b, x-2, y-2)
    except:
        pass

    try:

        if b.getEntry(x+1, y-1) in range(1, 7):
            if (b.getEntry(x+2, y-2) == 0):
                b.setEntry(x+2, y-2, 12)
                validMoveJump(b, x+2, y-2)

    except:
        pass

    try:

        if b.getEntry(x-1, y+1) in range(1, 7):
            if (b.getEntry(x-2, y+2) == 0):
                b.setEntry(x-2, y+2, 12)
                validMoveJump(b, x-2, y+2)

    except:

        pass



def movePiece(b, x , y):
    b.setEntry(x, y, b.currentPlayer)
    b.resetAfterMoves()
    b.currentPlayerIncrement()
    if b.checkWin():
        print("WIN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

while running:
    clock.tick(60)
    window.fill((255, 255, 255))
    if b.currentPlayer ==2:
        ai.makeTheMove(b)

    for e in pg.event.get():

        if e.type == pg.MOUSEBUTTONDOWN:
            (mouseX, mouseY) = pg.mouse.get_pos()
            (xCoord, yCoord) = (mouseY//rectWidth, mouseX//rectHeight)
            print(xCoord, yCoord)
            if b.getEntry(xCoord, yCoord) == b.currentPlayer:
                b.resetPossibleMoves()
                b.setEntry(xCoord, yCoord, 11)
                validMoveWalk(b, xCoord, yCoord)
                validMoveJump(b, xCoord, yCoord)

            elif b.getEntry(xCoord, yCoord) == 11:
                b.resetPossibleMoves()

            elif b.getEntry(xCoord, yCoord) == 12:
                movePiece(b, xCoord, yCoord)

        if e.type == pg.QUIT:
            running = False

    #board printing
    for i in range(b.length1):
        for j in range(b.length2):
            if b.getEntry(i, j) == 0 :
                pg.draw.rect(window, COLOR0, (j*rectHeight, i*rectWidth, rectHeight, rectWidth))

            elif b.getEntry(i, j) == 1:
                pg.draw.rect(window, COLOR1, (j*rectHeight, i*rectWidth, rectHeight, rectWidth))

            elif  b.getEntry(i, j) == 2:
                pg.draw.rect(window, COLOR2, (j*rectHeight, i*rectWidth, rectHeight, rectWidth))

            elif  b.getEntry(i, j) == 11:
                pg.draw.rect(window, COLOR11, (j*rectHeight, i*rectWidth, rectHeight, rectWidth))

            elif  b.getEntry(i, j) == 12:
                pg.draw.rect(window, COLOR12, (j*rectHeight, i*rectWidth, rectHeight, rectWidth))

            else:
                pg.draw.rect(window, COLOR9, (j*rectHeight, i*rectWidth, rectHeight, rectWidth))

    pg.display.flip()

pg.quit()
